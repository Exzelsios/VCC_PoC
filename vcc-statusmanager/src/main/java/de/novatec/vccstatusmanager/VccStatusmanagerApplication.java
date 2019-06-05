package de.novatec.vccstatusmanager;

import de.novatec.vccstatusmanager.entity.LoadStatus;
import de.novatec.vccstatusmanager.entity.TargetStatus;
import de.novatec.vccstatusmanager.kubernetes.InvalidOperationException;
import de.novatec.vccstatusmanager.kubernetes.KubernetesManager;
import de.novatec.vccstatusmanager.repository.DataPoolStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableScheduling
@SpringBootApplication
public class VccStatusmanagerApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VccStatusmanagerApplication.class);

	@Autowired
	private KubernetesManager kubernetesManager;

	@Autowired
    private DataPoolStatusService dataPoolStatusService;

	public static void main(String[] args) {
		SpringApplication.run(VccStatusmanagerApplication.class, args);
	}

	@Scheduled(fixedRate = 10000)
	public void updateDeploymentStatus() {
		List<Long> deployedDatapools = kubernetesManager.getLoadedDataPoolIds();
        List<TargetStatus> targetStatuses = dataPoolStatusService.findAll();
        LOGGER.info("targetStatuses=" + targetStatuses.toString());

        for(TargetStatus targetStatus : targetStatuses) {

            if(targetStatus.getTargetStatus() == LoadStatus.LOADED) {
                if(!deployedDatapools.contains(targetStatus.getDataPoolId())) {
                    try {
                        kubernetesManager.createVccServiceDeploymentAndService(targetStatus.getDataPoolId());
                    } catch (InvalidOperationException e) {
                        e.printStackTrace();
                    }
                }
            }

            if(targetStatus.getTargetStatus() == LoadStatus.UNLOADED) {
                if(deployedDatapools.contains(targetStatus.getDataPoolId())) {
                    kubernetesManager.deleteVccServiceDeploymentAndService(targetStatus.getDataPoolId());
                }
            }
        }

        deployedDatapools.stream().filter(x -> targetStatuses.stream()
                .filter(ts -> ts.getDataPoolId().equals(x))
                .count() == 0)
                .forEach(id -> {
                        kubernetesManager.deleteVccServiceDeploymentAndService(id);
                        LOGGER.info("Deleted orphaned service/deployment for datapoolId " + id);
                });
	}

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}