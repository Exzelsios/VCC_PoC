package de.novatec.vccregistry.resource;

import de.novatec.vccregistry.DataPoolCache;
import de.novatec.vccregistry.datapoolstatus.repository.DataPoolStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/vccrest/registry")
public class DataPoolRestResource {

    private static final Logger LOG = LoggerFactory.getLogger(DataPoolRestResource.class);

    @Autowired
    private DataPoolCache dataPoolCache;

    @Autowired
    private DataPoolStatusService dataPoolStatusService;

    @Value("${application.serverVersion}")
    private String serverVersion;

    @GetMapping(path = "/status")
    public String getStatus() {
        return "vcc-registry is running.";
    }

    @RequestMapping(path = "/getDataPools", method = POST)
    public GetDataPoolsResponse getDataPools(@RequestBody GetDataPoolsrequest getDataPoolsrequest) {

        GetDataPoolsResponse response = new GetDataPoolsResponse();
        response.setServerVersion(serverVersion);
        response.setDataPools(dataPoolCache.getDatapoolsByFilter(getDataPoolsrequest));

        return response;
    }
}