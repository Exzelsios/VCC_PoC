package de.novatec.vccclient.datapoolstatus.repository;

import de.novatec.vccclient.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccclient.datapoolstatus.entity.TargetStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class RestDataPoolStatusService implements DataPoolStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestDataPoolStatusService.class);

    @Autowired
    private RestTemplate restTemplate;

    private final String DATAPOOLS_ENDPOINT;
    private final String TARGETSTATUS_ENDPOINT;

    @Autowired
    public RestDataPoolStatusService(
            @Value("${VCC_STATUS_REPOSITORY_SERVICE_HOST:localhost}") String serviceHost,
            @Value("${VCC_STATUS_REPOSITORY_SERVICE_PORT:8080}") int servicePort) {

        DATAPOOLS_ENDPOINT = buildHttpUrl(serviceHost, servicePort) + "datapoolstatus";
        TARGETSTATUS_ENDPOINT = buildHttpUrl(serviceHost, servicePort) + "targetstatus";

        LOGGER.info("DATAPOOLS_ENDPOINT=" + DATAPOOLS_ENDPOINT);
        LOGGER.info("TARGETSTATUS_ENDPOINT=" + TARGETSTATUS_ENDPOINT);
    }

    private String buildHttpUrl(String host, int port) {
        return "http://" + host + ":" + port + "/";
    }

    @Override
    public Optional<DataPoolStatus> findByDataVersion(Long dataVersion) {
        try {
            ResponseEntity<DataPoolStatus> responseEntity = restTemplate.getForEntity(DATAPOOLS_ENDPOINT + "/" + dataVersion, DataPoolStatus.class);
            return Optional.ofNullable(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            LOGGER.warn("Received " + ex.getMessage() + " when looking for datapoolstatus in vcc-status-repository for id=" + dataVersion);
            return Optional.empty();
        }
    }

    @Override
    public Optional<TargetStatus> findTargetStatusByDataVersion(Long dataVersion) {
        try {
            ResponseEntity<TargetStatus> responseEntity = restTemplate.getForEntity(TARGETSTATUS_ENDPOINT + "/" + dataVersion, TargetStatus.class);
            return Optional.ofNullable(responseEntity.getBody());
        } catch (HttpClientErrorException ex) {
            LOGGER.warn("Received " + ex.getMessage() + " when looking for targetstatus in vcc-status-repository for id=" + dataVersion);
            return Optional.empty();
        }
    }

    @Override
    public void deleteByDataVersion(Long dataVersion) {
        // first delete the targetStatus
        restTemplate.delete(TARGETSTATUS_ENDPOINT + "/" + dataVersion);
        restTemplate.delete(DATAPOOLS_ENDPOINT + "/" + dataVersion);
    }
}
