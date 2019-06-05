package de.novatec.vccregistry.kubernetes;

import io.fabric8.kubernetes.api.model.IntOrString;
import io.fabric8.kubernetes.api.model.ProbeBuilder;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.api.model.extensions.DeploymentBuilder;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

    public KubernetesManager() {
        Config config = new ConfigBuilder()
                .withUsername(username)
                .withPassword(password)
                .build();

        kubernetesClient = new DefaultKubernetesClient(config);
    }

    public List<Long> getLoadedDataPoolIds() {

        Predicate<Deployment> readyDeploymentFilter = d -> d != null && d.getStatus() != null &&
                d.getStatus().getReadyReplicas() != null &&
                d.getStatus().getReadyReplicas() > 0;

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

    public void deleteVccServiceDeploymentAndService(Long datapoolId) {
        String specificServiceName = buildServiceNameByDataPoolId(datapoolId);
        kubernetesClient.services().withName(specificServiceName).delete();
        LOGGER.info("Deleted service " + specificServiceName);

        String specificDeploymentName = buildDeploymentNameByDataPoolId(datapoolId);
        kubernetesClient.extensions().deployments().withName(specificDeploymentName).delete();
        LOGGER.info("Deleted deployments with name " + specificDeploymentName);
    }

    public void createVccServiceDeploymentAndService(Long datapoolId) throws InvalidOperationException {

        // Input validation
        if(kubernetesClient.services().withName(buildServiceNameByDataPoolId(datapoolId)).get() != null) {
            throw new InvalidOperationException("Service for datapool " + datapoolId + " already exists.");
        }

        if(kubernetesClient.extensions().deployments().withName(buildDeploymentNameByDataPoolId(datapoolId)).get() != null) {
            throw new InvalidOperationException("Deployment for datapool " + datapoolId + " already exists.");
        }

        io.fabric8.kubernetes.api.model.Service service = kubernetesClient.services().createNew()
                .withNewSpec()
                .withType("LoadBalancer")
                .withPorts(new ServicePortBuilder()
                    .withName(serviceName.toLowerCase() + "-service-port")
                    .withPort(8080)
                    .withProtocol("TCP")
                    .build())
                .addToSelector("app", appName + datapoolId)
                .endSpec()
                .withNewMetadata()
                .withName(buildServiceNameByDataPoolId(datapoolId))
                .endMetadata()
                .done();

        LOGGER.info("Created vcc-service with name " + service.getMetadata().getName() + " for datapoolId " + datapoolId);

        Deployment deployment = new DeploymentBuilder()
                .withNewMetadata()
                .withName(buildDeploymentNameByDataPoolId(datapoolId))
                .endMetadata()
                .withNewSpec()
                .withReplicas(initialReplicas)
                .withNewTemplate()
                .withNewMetadata()
                .addToLabels("app", appName + datapoolId)
                .endMetadata()
                .withNewSpec()
                .addNewContainer()
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
                .withName("DATAPOOLID")
                .withValue(Long.toString(datapoolId))
                .endEnv()
                .addNewPort()
                .withContainerPort(containerPort)
                .endPort()
                .endContainer()
                .endSpec()
                .endTemplate()
                .endSpec()
                .build();

        deployment = kubernetesClient.extensions().deployments().create(deployment);
        LOGGER.info("Created vcc-service deployment with name " + deployment.getMetadata().getName() + " and datapoolId " + datapoolId);
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
