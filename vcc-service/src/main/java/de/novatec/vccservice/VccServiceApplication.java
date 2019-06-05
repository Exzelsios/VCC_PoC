package de.novatec.vccservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

@SpringBootApplication
public class VccServiceApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(VccServiceApplication.class);

    @Autowired
    private DataPoolCache dataPoolCache;
    
	public static void main(String[] args) {
	    LOGGER.info("JAVA ARGS: " + args);
	    SpringApplication.run(VccServiceApplication.class, args);
	}

    @PostConstruct
    public void init() {
        RuntimeMXBean runtimeMxBean = ManagementFactory.getRuntimeMXBean();
        List<String> arguments = runtimeMxBean.getInputArguments();
        arguments.forEach(arg -> LOGGER.info("VM ARG: " + arg));

	    dataPoolCache.initializePayload();
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
