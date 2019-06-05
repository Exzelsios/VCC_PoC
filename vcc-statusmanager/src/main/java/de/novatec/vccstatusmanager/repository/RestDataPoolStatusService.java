package de.novatec.vccstatusmanager.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.novatec.vccstatusmanager.entity.DataPoolStatus;
import de.novatec.vccstatusmanager.entity.TargetStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("RestDataPoolStatusService")
public class RestDataPoolStatusService implements DataPoolStatusService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestDataPoolStatusService.class);

    @Autowired
    private RestTemplate restTemplate;

    private final String GET_BY_ID_URL;
    private final String TARGETSTATUS_ENDPOINT;

    @Autowired
    public RestDataPoolStatusService(
            @Value("${VCC_STATUS_REPOSITORY_SERVICE_HOST:localhost}") String serviceHost,
            @Value("${VCC_STATUS_REPOSITORY_SERVICE_PORT:8080}") int servicePort) {

        GET_BY_ID_URL = buildHttpUrl(serviceHost, servicePort) + "datapoolstatus/";
        TARGETSTATUS_ENDPOINT = buildHttpUrl(serviceHost, servicePort) + "targetstatus";
    }

    private String buildHttpUrl(String host, int port) {
        return "http://" + host + ":" + port + "/";
    }

    @Override
    public Optional<DataPoolStatus> findByDataVersion(Long dataVersion) {
        String requestUrl = GET_BY_ID_URL + dataVersion;

        LOGGER.info("REQUEST_URL=" + requestUrl);

        ResponseEntity<DataPoolStatus> responseEntity = restTemplate.getForEntity(requestUrl, DataPoolStatus.class);
        return Optional.ofNullable(responseEntity.getBody());
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
    public List<TargetStatus> findAll() {

        ResponseEntity<Resources<TargetStatus>> rateResponse =
                getHalRestTemplate().exchange(TARGETSTATUS_ENDPOINT,
                        HttpMethod.GET, null, new ParameterizedTypeReference<Resources<TargetStatus>>(){});
        List<TargetStatus> targetStatuses = new ArrayList<>(rateResponse.getBody().getContent());

        return targetStatuses;
    }

    @Override
    public void updateTargetStatus(TargetStatus targetStatus) {
        restTemplate.put(TARGETSTATUS_ENDPOINT + "/" + targetStatus.getDataPoolId(), targetStatus);
    }

    private RestTemplate getHalRestTemplate() {
        RestTemplate rest = new RestTemplate();

        //need to create a new message converter to handle hal+json
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(MediaType.parseMediaTypes("application/hal+json"));
        converter.setObjectMapper(mapper);

        //add the new converters to the restTemplate
        //but make sure it is BEFORE the exististing converters
        List<HttpMessageConverter<?>> converters = rest.getMessageConverters();
        converters.add(0,converter);
        rest.setMessageConverters(converters);
        return rest;
    }
}
