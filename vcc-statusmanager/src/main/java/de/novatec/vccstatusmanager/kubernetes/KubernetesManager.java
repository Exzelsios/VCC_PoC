package de.novatec.vccstatusmanager.kubernetes;

import de.novatec.vccstatusmanager.datapoolmeta.entity.DataPoolMeta;
import de.novatec.vccstatusmanager.datapoolmeta.repository.DataPoolMetaService;
import io.fabric8.kubernetes.api.model.*;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentBuilder;
import io.fabric8.kubernetes.api.model.extensions.Ingress;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KubernetesManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(KubernetesManager.class);

    private KubernetesClient kubernetesClient;

    @Autowired
    private DataPoolMetaService dataPoolMetaService;

    @Value("${kubernetes.username}")
    private String username;

    @Value("${kubernetes.password}")
    private String password;

    @Value("${kubernetes.template.image}")
    private String image;

    @Value("${kubernetes.template.containerPort}")
    private int containerPort;

    @Value("${kubernetes.template.deploymentName}")
    private String deploymentName;

    @Value("${kubernetes.template.serviceName}")
    private String serviceName;

    @Value("${kubernetes.template.containerName}")
    private String containerName;

    @Value("${kubernetes.template.initialReplicas}")
    private int initialReplicas;

    @Value("${kubernetes.template.appName}")
    private String appName;

    private final int BASE_MEMORY_LIMIT = 265;
    private final int BASE_XMX = 64;

    public KubernetesManager() {
        Config config = new ConfigBuilder()
                .withUsername(username)
                .withPassword(password)
                .build();

        kubernetesClient = new DefaultKubernetesClient(config);
    }

    public List<Long> getLoadedDataPoolIds() {

        List<Long> availableDeployments = kubernetesClient.extensions().deployments()
                .list()
                .getItems()
                .stream()
                .filter(deploy -> deploy.getMetadata().getLabels() != null && deploy.getMetadata().getLabels().containsKey("app") ? deploy.getMetadata().getLabels().get("app").startsWith(appName) : false)
                .map(d -> {
                    String[] nameSplit = d.getMetadata().getName().split("-");
                    return Long.parseLong(nameSplit[nameSplit.length - 1]);
                })
                .collect(Collectors.toList());

        return availableDeployments;
    }

    public void deleteVccServiceDeploymentAndService(Long datapoolId) {
        kubernetesClient.services().withName(buildServiceNameByDataPoolId(datapoolId)).delete();
        LOGGER.info("Deleted service " + serviceName + "-" + datapoolId);

        kubernetesClient.autoscaling().horizontalPodAutoscalers().withName(buildDeploymentNameByDataPoolId(datapoolId)).delete();
        LOGGER.info("Deleted autoscaler with name " + buildDeploymentNameByDataPoolId(datapoolId));

        kubernetesClient.extensions().deployments().withName(buildDeploymentNameByDataPoolId(datapoolId)).delete();
        LOGGER.info("Deleted deployment " + buildDeploymentNameByDataPoolId(datapoolId) + "\nDeleting ingress next.");
    }

    public void createVccServiceDeploymentAndService(Long datapoolId) throws InvalidOperationException {

        io.fabric8.kubernetes.api.model.Service service;

        if (kubernetesClient.services().withName(buildServiceNameByDataPoolId(datapoolId)).get() != null) {
            service = kubernetesClient.services().withName(buildServiceNameByDataPoolId(datapoolId)).get();
            LOGGER.info("Service for datapoolId " + datapoolId + " already exists. Not creating a new one.");
        } else {
            service = kubernetesClient.services().createNew()
                    .withNewSpec()
                    .withType("NodePort")
                    .withPorts(new ServicePortBuilder()
                            .withName(serviceName.toLowerCase() + "-service-port")
                            .withPort(8080)
                            .withProtocol("TCP")
                            .build())
                    .addToSelector("app", appName + "-" + datapoolId)
                    .endSpec()
                    .withNewMetadata()
                    .withName(buildServiceNameByDataPoolId(datapoolId))
                    .endMetadata()
                    .done();

            LOGGER.info("Created vcc-service with name " + service.getMetadata().getName() + " for datapoolId " + datapoolId);
        }

        Optional<DataPoolMeta> dataPoolMetaOptional = dataPoolMetaService.findByDataVersion(datapoolId);
        DataPoolMeta dataPoolMeta;

        if(dataPoolMetaOptional.isPresent()) {
            dataPoolMeta = dataPoolMetaOptional.get();
            LOGGER.info("Fetched datapoolmeta from db = " + dataPoolMeta.toString());
        } else {
            dataPoolMeta = new DataPoolMeta();
            dataPoolMeta.setLoadDelay(1);
            dataPoolMeta.setPayloadSize(10000);
        }

        int allocationInMegabytes = (int)((((float)dataPoolMeta.getPayloadSize() / 1000000.0f) + 25f) * 2.0f);
        int memoryLimit = BASE_MEMORY_LIMIT + allocationInMegabytes;
        int xmx = BASE_XMX + allocationInMegabytes;
        LOGGER.info("Target memory limit for payloadsize " + dataPoolMeta.getPayloadSize() + " : " + memoryLimit + " -Xmx" + xmx + "Mi");

        if (kubernetesClient.extensions().deployments().withName(buildDeploymentNameByDataPoolId(datapoolId)).get() != null) {
            LOGGER.info("Deployment for datapoolId " + datapoolId + " already exists. Not creating a new one.");
        } else {
            Deployment deployment = new DeploymentBuilder()
                    .withNewMetadata()
                    .withName(buildDeploymentNameByDataPoolId(datapoolId))
                    .endMetadata()
                    .withNewSpec()
                    .withReplicas(initialReplicas)
                    .withNewTemplate()
                    .withNewMetadata()
                        .addToLabels("app", appName + "-" + datapoolId)
                        .addToAnnotations("prometheus.io/scrape", "true")
                        .addToAnnotations("prometheus.io/path", "/actuator/prometheus")
                        .addToAnnotations("prometheus.io/port", "8080")
                    .endMetadata()
                    .withNewSpec()
                    .addNewImagePullSecret("regcred")
                    .addNewContainer()
                    .withNewResources()
                        .addToRequests("memory", new Quantity("64Mi"))
                        .addToLimits("cpu", new Quantity("200m"))
                        .addToLimits("memory", new Quantity( memoryLimit + "Mi"))
                    .endResources()
                    .withName(buildContainerNameByDataPoolId(datapoolId))
                    .withImage(image)
                    .withReadinessProbe(new ProbeBuilder()
                            .withNewHttpGet()
                            .withPath("/ready")
                            .withPort(new IntOrString(8080))
                            .endHttpGet()
                            .withFailureThreshold(1)
                            .withInitialDelaySeconds(1)
                            .withPeriodSeconds(1)
                            .withSuccessThreshold(1)
                            .withTimeoutSeconds(1)
                            .build())
                    .addNewEnv()
                        .withName("JVM_OPTS")
                        .withValue("-Xmx" + xmx + "M")
                    .endEnv()
                    .addNewEnv()
                        .withName("DATAPOOLID")
                        .withValue(Long.toString(datapoolId))
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_AGENT_ACCOUNT_NAME")
                        .withValue("customer1")
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_AGENT_TIER_NAME")
                        .withValue(buildDeploymentNameByDataPoolId(datapoolId))
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_AGENT_APPLICATION_NAME")
                        .withValue("VCC")
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_AGENT_ACCOUNT_ACCESS_KEY")
                        .withValue("675e50a8-5671-4013-9bec-9a72d32cb42d")
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_CONTROLLER_PORT")
                        .withValue("8090")
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_CONTROLLER_HOST_NAME")
                        .withValue("10.85.216.144")
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_NODE_PREFIX")
                        .withValue("Instance")
                    .endEnv()
                    .addNewEnv()
                        .withName("APPDYNAMICS_AGENT_NODE_NAME")
                        .withValue("Node")
                    .endEnv()
                    .addNewPort()
                        .withContainerPort(containerPort)
                    .endPort()
                    .addNewPort()
                        .withContainerPort(9010)
                    .endPort()
                    .endContainer()
                    .endSpec()
                    .endTemplate()
                    .endSpec()
                    .build();

            deployment = kubernetesClient.extensions().deployments().create(deployment);
            LOGGER.info("Created vcc-service deployment with name " + deployment.getMetadata().getName() + " and datapoolId " + datapoolId);
        }

        if (kubernetesClient.autoscaling().horizontalPodAutoscalers().withName(buildDeploymentNameByDataPoolId(datapoolId)).get() != null) {
            LOGGER.info("Autoscaler for datapoolId " + datapoolId + " already exists. Not creating a new one.");
        } else {
            HorizontalPodAutoscaler autoscaler = new HorizontalPodAutoscalerBuilder()
                    .withNewMetadata()
                    .withName(buildDeploymentNameByDataPoolId(datapoolId))
                    .endMetadata()
                    .withNewSpec()
                    .withMaxReplicas(10)
                    .withMinReplicas(2)
                    .withTargetCPUUtilizationPercentage(80)
                    .withNewScaleTargetRef("extensions/v1beta1", "Deployment", buildDeploymentNameByDataPoolId(datapoolId))
                    .endSpec()
                    .build();

            kubernetesClient.autoscaling().horizontalPodAutoscalers().create(autoscaler);
            LOGGER.info("Created autoscaler for " + buildDeploymentNameByDataPoolId(datapoolId) + "\n" + autoscaler.toString());
        }

        Ingress ingress = kubernetesClient.extensions().ingresses().withName("vcc-ingress").get();
        if (ingress
                .getSpec()
                .getRules()
                .get(0)
                .getHttp()
                .getPaths()
                .stream()
                .filter(path -> path.getPath().equals("/vccrest/entity/getConfigurationContext/" + datapoolId))
                .count() > 0) {
            LOGGER.info("Ingress backend for service " + buildServiceNameByDataPoolId(datapoolId) + " already exists. Not creating a new one.");
        } else {
            kubernetesClient.extensions().ingresses().withName("vcc-ingress")
                    .edit()
                    .editSpec()
                    .editFirstRule()
                    .editHttp()
                    .addNewPath()
                    .withPath("/vccrest/entity/getConfigurationContext/" + datapoolId)
                    .editOrNewBackend()
                    .withServiceName(buildServiceNameByDataPoolId(datapoolId))
                    .withServicePort(new IntOrString(service.getSpec().getPorts().get(0).getPort()))
                    .endBackend()
                    .endPath()
                    .endHttp()
                    .endRule()
                    .endSpec()
                    .done();

            LOGGER.info("Created ingress for vcc-service-service-" + datapoolId);
        }
    }

    private String buildContainerNameByDataPoolId(Long id) {
        return containerName + "-" + id;
    }

    private String buildServiceNameByDataPoolId(Long id) {
        return serviceName + "-" + id;
    }

    private String buildDeploymentNameByDataPoolId(Long id) {
        return deploymentName + "-" + id;
    }
}
