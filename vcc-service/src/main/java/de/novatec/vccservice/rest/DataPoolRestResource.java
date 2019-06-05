package de.novatec.vccservice.rest;

import de.novatec.vccservice.DataPoolCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
public class DataPoolRestResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataPoolRestResource.class);

    @Autowired
    private DataPoolCache dataPoolCache;

    @GetMapping(path = "/ready")
    public ResponseEntity<?> isReady() {
        return dataPoolCache.isReady() ?
                new ResponseEntity<>(HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping(path = "/vccrest/entity/getConfigurationContext/{dataPoolId}")
    public GetConfigurationContextResponse getConfigurationContext(@PathVariable String dataPoolId, @RequestBody GetConfigurationContextRequest request) throws UnknownHostException {

        LOGGER.info("Handling request to getConfigurationContext");

        ConfigurationContext configurationContext = new ConfigurationContext();
        configurationContext.setConfigurationDate(request.getConfigurationDate());
        configurationContext.setCorrelationId(12341);
        configurationContext.setCountryId(request.getCountryId());
        configurationContext.setDataPool(dataPoolCache.getDataPool());

        GetConfigurationContextResponse response = new GetConfigurationContextResponse();
        response.setResponse(InetAddress.getLocalHost().getHostName());
        response.setServerVersion("0.0.1");
        response.setConfigurationContext(configurationContext);

        return response;
    }
}
