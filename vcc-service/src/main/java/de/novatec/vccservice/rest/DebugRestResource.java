package de.novatec.vccservice.rest;

import de.novatec.vccservice.ApplicationProperties;
import de.novatec.vccservice.LoadSimulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DebugRestResource {

    private final static Logger LOGGER = LoggerFactory.getLogger(DebugRestResource.class);

    @Autowired
    private LoadSimulator loadSimulator;

    @Value("${appVersion}")
    private String appVersion;

    @GetMapping("/kill")
    public void kill() {
        System.exit(-1);
    }

    @GetMapping("/load/start")
    public ResponseEntity<?> startLoad() {
        loadSimulator.startCpuLoad();
        LOGGER.info("Started CPU load.");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/load/stop")
    public ResponseEntity<?> stopLoad() {
        loadSimulator.stopCpuLoad();
        LOGGER.info("Stopped CPU load.");

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/properties")
    public ApplicationProperties getProperties() {
        return new ApplicationProperties(appVersion, loadSimulator.isOverloading());
    }
}
