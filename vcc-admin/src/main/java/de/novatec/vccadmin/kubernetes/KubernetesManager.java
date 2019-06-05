package de.novatec.vccadmin.kubernetes;

import io.fabric8.kubernetes.api.model.extensions.Deployment;
import io.fabric8.kubernetes.client.Config;
import io.fabric8.kubernetes.client.ConfigBuilder;
import io.fabric8.kubernetes.client.DefaultKubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;

    public KubernetesManager() {
        Config config = new ConfigBuilder()
                .withUsername(username)
                .withPassword(password)
                .build();

        kubernetesClient = new DefaultKubernetesClient(config);
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
