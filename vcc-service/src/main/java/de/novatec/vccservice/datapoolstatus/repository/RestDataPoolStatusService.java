package de.novatec.vccservice.datapoolstatus.repository;

import de.novatec.vccservice.datapoolstatus.entity.DataPoolStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Optional;

@Service
public class RestDataPoolStatusService implements DataPoolStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestDataPoolStatusService.class);

    @Autowired
    private RestTemplate restTemplate;

    private final String DATAPOOLS_ENDPOINT;

    @Autowired
    public RestDataPoolStatusService(
            @Value("${VCC_STATUS_REPOSITORY_SERVICE_HOST:localhost}") String serviceHost,
            @Value("${VCC_STATUS_REPOSITORY_SERVICE_PORT:8080}") int servicePort) {

        DATAPOOLS_ENDPOINT = buildHttpUrl(serviceHost, servicePort) + "datapoolstatus";

        LOGGER.info("DATAPOOLS_ENDPOINT=" + DATAPOOLS_ENDPOINT);
    }

    private String buildHttpUrl(String host, int port) {
        return "http://" + host + ":" + port + "/";
    }

    @Override
    public Optional<DataPoolStatus> findByDataVersion(Long dataVersion) {
        ResponseEntity<DataPoolStatus> responseEntity = restTemplate.getForEntity(DATAPOOLS_ENDPOINT + "/" + dataVersion, DataPoolStatus.class);
        return Optional.ofNullable(responseEntity.getBody());
    }

    @Override
    public DataPoolStatus save(DataPoolStatus dataPoolStatus) {
        return restTemplate.postForEntity(URI.create(DATAPOOLS_ENDPOINT), dataPoolStatus, DataPoolStatus.class).getBody();
    }
}
