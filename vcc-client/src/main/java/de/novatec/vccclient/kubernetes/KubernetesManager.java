package de.novatec.vccclient.kubernetes;

import de.novatec.vccclient.datapool.entity.DebugDataPool;
import de.novatec.vccclient.datapool.entity.DebugPod;
import de.novatec.vccclient.debug.ServiceProperties;
import de.novatec.vccclient.util.AsyncHttpRequest;
import io.fabric8.kubernetes.api.model.HorizontalPodAutoscaler;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.ocpsoft.prettytime.PrettyTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class KubernetesManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KubernetesManager.class);

    private KubernetesClient kubernetesClient;

    @Value("${kubernetes.username}")
    private String username;

    @Value("${kubernetes.password}")
    private String password;

    @Value("${kubernetes.template.appName}")
    private String appName;

    @Autowired
    private AsyncHttpRequest asyncHttpRequest;

    @Autowired
    private RestTemplate restTemplate;

    public KubernetesManager() {
        Config config = new ConfigBuilder()
                .withUsername(username)
                .withPassword(password)
                .build();

        kubernetesClient = new DefaultKubernetesClient(config);
    }

    public void stopOverloadPodById(String podId) {
        LOGGER.info("Stopping overload on pod " + podId);
        Pod podToLoad = kubernetesClient.pods().withName(podId).get();
        String ipEndpoint = podToLoad.getStatus().getPodIP();

        try {
            asyncHttpRequest.execute(ipEndpoint, "/load/stop");
        } catch (Exception e) {
            LOGGER.error("Failed to stop overloading.", e);
        }
    }

    public void overloadPodById(String podId) {
        LOGGER.info("Overloading pod " + podId);
        Pod podToLoad = kubernetesClient.pods().withName(podId).get();
        String ipEndpoint = podToLoad.getStatus().getPodIP();

        try {
            asyncHttpRequest.execute(ipEndpoint, "/load/start");
        } catch (Exception e) {
            LOGGER.error("Failed to start overloading.", e);
        }
    }

    public void killPodById(String podId) {
        LOGGER.info("Killing pod " + podId);
        Pod podToKill = kubernetesClient.pods().withName(podId).get();
        String ipEndpoint = podToKill.getStatus().getPodIP();

        try {
            asyncHttpRequest.execute(ipEndpoint, "/kill");
        } catch (Exception e) {
            LOGGER.error("Failed to kill.", e);
        }
    }

    public List<DebugDataPool> getDebugDataPools() {
        List<DebugDataPool> debugDataPools = new ArrayList<>();
        List<Long> deployedIds = getLoadedAndUnreadyDataPoolIds();

        deployedIds.forEach(id -> {
            DebugDataPool debugDataPool = new DebugDataPool();
            debugDataPool.setDataVersion(id);

            HorizontalPodAutoscaler autoscaler = kubernetesClient.autoscaling()
                    .horizontalPodAutoscalers()
                    .withName(buildDeploymentNameByDatapoolId(id))
                    .get();

            if(autoscaler != null && autoscaler.getStatus() != null) {
                debugDataPool.setActualReplicas(autoscaler.getStatus().getCurrentReplicas());
                debugDataPool.setDesiredReplicas(autoscaler.getStatus().getDesiredReplicas());
                debugDataPool.setCurrentCpuUtilization(autoscaler.getStatus().getCurrentCPUUtilizationPercentage());

                if (autoscaler.getStatus().getLastScaleTime() != null) {

                    DateTimeFormatter sourceFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
                    LocalDateTime lastScaleTime = LocalDateTime.parse(autoscaler.getStatus().getLastScaleTime(), sourceFormat);
                    PrettyTime prettyTime = new PrettyTime();

                    debugDataPool.setLastScaleTime(prettyTime.format(Date.from(lastScaleTime.atZone(ZoneId.systemDefault()).toInstant())));
                } else {
                    debugDataPool.setLastScaleTime("N/A");
                }
            }

            Deployment deployment = kubernetesClient.extensions().deployments()
                    .withName(buildDeploymentNameByDatapoolId(id))
                    .get();

            debugDataPool.setDeploymentName(buildDeploymentNameByDatapoolId(id));
            debugDataPool.setImage(deployment.getSpec().getTemplate().getSpec().getContainers().get(0).getImage());

            kubernetesClient.pods()
                    .withLabel("app", buildDeploymentNameByDatapoolId(id))
                    .list()
                    .getItems()
                    .forEach(pod -> {
                        try {
                            ServiceProperties serviceProperties = restTemplate.getForEntity("http://" + pod.getStatus().getPodIP() + ":8080/properties", ServiceProperties.class).getBody();
                            debugDataPool.getPods().add(
                                    new DebugPod(pod.getMetadata().getName(),
                                    pod.getStatus().getPodIP(),
                                    serviceProperties.isCpuOverload() ? "CPU overload" : "idle",
                                    pod.getSpec().getContainers().get(0).getImage()));
                        } catch (Exception e) {
                            debugDataPool.getPods().add(new DebugPod(pod.getMetadata().getName(), pod.getStatus().getPodIP(), "N/A", pod.getSpec().getContainers().get(0).getImage()));
                        }
                    });

            debugDataPools.add(debugDataPool);
        });
        return debugDataPools;
    }

    public List<Long> getLoadedAndUnreadyDataPoolIds() {
        Predicate<Deployment> readyDeploymentFilter = d -> {
            return d != null && d.getStatus() != null;
        };

        List<Long> availableDeployments = kubernetesClient.extensions().deployments()
                .list()
                .getItems()
                .stream()
                .filter(deploy -> deploy.getMetadata().getLabels() != null && deploy.getMetadata().getLabels().containsKey("app") ? deploy.getMetadata().getLabels().get("app").startsWith(appName) : false)
                .filter(readyDeploymentFilter)
                .map(d -> {
                    String[] nameSplit = d.getMetadata().getName().split("-");
                    return Long.parseLong(nameSplit[nameSplit.length -1]);
                })
                .collect(Collectors.toList());

        return availableDeployments;
    }

    public List<Long> getLoadedDataPoolIds() {

        Predicate<Deployment> readyDeploymentFilter = d -> {
                return d != null && d.getStatus() != null &&
                        d.getStatus().getReadyReplicas() != null &&
                        d.getStatus().getReadyReplicas() > 0;
        };

        List<Long> availableDeployments = kubernetesClient.extensions().deployments()
                .list()
                .getItems()
                .stream()
                .filter(deploy -> deploy.getMetadata().getLabels() != null && deploy.getMetadata().getLabels().containsKey("app") ? deploy.getMetadata().getLabels().get("app").startsWith(appName) : false)
                .filter(readyDeploymentFilter)
                .map(d -> {
                    String[] nameSplit = d.getMetadata().getName().split("-");
                    return Long.parseLong(nameSplit[nameSplit.length -1]);
                })
                .collect(Collectors.toList());

        return availableDeployments;
    }

    private String buildDeploymentNameByDatapoolId(Long id) {
        return "vcc-service-" + id;
    }
}
