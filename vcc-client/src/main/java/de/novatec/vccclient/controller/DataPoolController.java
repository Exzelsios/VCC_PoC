package de.novatec.vccclient.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.novatec.vccclient.DataPoolCache;
import de.novatec.vccclient.datapool.entity.ViewDataPool;
import de.novatec.vccclient.datapool.entity.rest.DataPoolIdentifier;
import de.novatec.vccclient.datapool.entity.rest.GetConfigurationContextRequest;
import de.novatec.vccclient.datapool.entity.rest.GetConfigurationContextResponse;
import de.novatec.vccclient.datapool.entity.rest.RestDataPool;
import de.novatec.vccclient.registry.GetDataPoolsResponse;
import de.novatec.vccclient.registry.GetDataPoolsrequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class DataPoolController {
    //private final String SERVICE_ENDPOINT = "http://vcc-development.eu-central.containers.mybluemix.net/vccrest/entity/getConfigurationContext/";
    //private final String REGISTRY_ENDPOINT = "http://vcc-development.eu-central.containers.mybluemix.net/vccrest/registry/getDataPools";

    private final String SERVICE_DNS_NAME = "http://vcc-service-service-%s:8080/vccrest/entity/getConfigurationContext/%s";
    private final String REGISTRY_DNS_NAME = "http://vcc-registry:8080/vccrest/registry/getDataPools";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DataPoolCache dataPoolCache;

    private Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

    @GetMapping(path = { "/client" })
    public String dataPoolstatusList(Model model) {
        List<ViewDataPool> dataPools = dataPoolCache.getLoadedDataPools();

        Collections.sort(dataPools, new Comparator<ViewDataPool>() {
            @Override
            public int compare(ViewDataPool o1, ViewDataPool o2) {
                return o1.getStatus().compareTo(o2.getStatus());
            }
        });

        GetDataPoolsrequest getDataPoolsrequest = new GetDataPoolsrequest();
        getDataPoolsrequest.setRequest("GetDataPools");

        model.addAttribute("datapools", dataPools);
        model.addAttribute("getDatapoolsRequest", getDataPoolsrequest);
        return "datapoolstatuslist";
    }


    @RequestMapping(path = "/client/registry", method = POST)
    public String queryRegistry(@ModelAttribute GetDataPoolsrequest getDataPoolsrequest, Model model) {

        try {
            GetDataPoolsResponse response = restTemplate.postForEntity(new URI(REGISTRY_DNS_NAME), getDataPoolsrequest, GetDataPoolsResponse.class).getBody();
            getDataPoolsrequest.setRequest("GetDataPools");
            model.addAttribute("request", gson.toJson(getDataPoolsrequest));
            model.addAttribute("response", gson.toJson(response));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "registryreponseview";
    }

    @GetMapping(path = "/client/datapool/query/{dataPoolId}")
    public String queryDataPool(@PathVariable String dataPoolId, Model model) {

        GetConfigurationContextRequest request = new GetConfigurationContextRequest();
        request.setRequest("GetConfigurationContext");
        request.setConfigurationDate("8346989254352");
        request.setCountryId(1);
        request.setDataPoolIdentifier(new DataPoolIdentifier(Long.parseLong(dataPoolId)));

        try {
            String serviceEndpoint = String.format(SERVICE_DNS_NAME, dataPoolId, dataPoolId);
            GetConfigurationContextResponse response = restTemplate.postForEntity(new URI(serviceEndpoint), request, GetConfigurationContextResponse.class).getBody();
            model.addAttribute("datapoolName", "Datapool " + response.getConfigurationContext().getDataPool().getDataVersion());
            model.addAttribute("response", gson.toJson(response));
            model.addAttribute("request", gson.toJson(request));
            model.addAttribute("serviceEndpoint", serviceEndpoint);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return "datapoolview";
    }
}
