package de.novatec.vccadmin.controller;

import com.google.gson.Gson;
import de.novatec.vccadmin.DataPoolCache;
import de.novatec.vccadmin.datapool.entity.CreateDataPool;
import de.novatec.vccadmin.datapool.entity.DataPool;
import de.novatec.vccadmin.datapool.repository.DataPoolService;
import de.novatec.vccadmin.datapoolstatus.entity.LoadStatus;
import de.novatec.vccadmin.datapoolstatus.entity.TargetStatus;
import de.novatec.vccadmin.datapoolstatus.repository.DataPoolStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.stream.Stream;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
public class DataPoolStatusListController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataPoolStatusListController.class);

    @Autowired
    private DataPoolStatusService dataPoolStatusService;

    @Autowired
    private DataPoolService dataPoolService;

    @Autowired
    private DataPoolCache dataPoolCache;

    @GetMapping(path = { "/" })
    public String dataPoolstatusList(Model model) {
        model.addAttribute("datapools", dataPoolCache.getAllDataPools());

        return "datapoolstatuslist";
    }

    @GetMapping(path = "/datapool/load/{dataPoolId}")
    public String loadDataPool(@PathVariable String dataPoolId) {

        Optional<TargetStatus> targetStatusOptional = dataPoolStatusService.findTargetStatusByDataVersion(Long.parseLong(dataPoolId));

        if(!targetStatusOptional.isPresent())
            return "redirect:/";

        TargetStatus targetStatus = targetStatusOptional.get();
        targetStatus.setTargetStatus(LoadStatus.LOADED);
        targetStatus.setDataPoolId(Long.parseLong(dataPoolId));
        dataPoolStatusService.updateTargetStatus(targetStatus);

        return "redirect:/";
    }

    @GetMapping(path = "/datapool/unload/{dataPoolId}")
    public String unloadDataPool(@PathVariable String dataPoolId) {

        Optional<TargetStatus> targetStatusOptional = dataPoolStatusService.findTargetStatusByDataVersion(Long.parseLong(dataPoolId));

        if(!targetStatusOptional.isPresent())
            return "redirect:/";

        TargetStatus targetStatus = targetStatusOptional.get();
        targetStatus.setTargetStatus(LoadStatus.UNLOADED);
        targetStatus.setDataPoolId(Long.parseLong(dataPoolId));
        dataPoolStatusService.updateTargetStatus(targetStatus);

        return "redirect:/";
    }

    @RequestMapping(path = "/datapool/add", method = POST)
    public String addDataPool(@ModelAttribute CreateDataPool createDataPool) throws DataPoolExistsException {

        LOGGER.info("CreateDataPool: " + createDataPool.toString());
        System.out.println("CreateDataPool: " + createDataPool.toString());

        int[] countries = Stream.of(createDataPool.getCountries().split(",")).mapToInt(Integer::parseInt).toArray();

        DataPool dataPool = new DataPool();
        dataPool.setDataVersion(createDataPool.getDataVersion());
        dataPool.setProductStructureId(createDataPool.getProductStructureId());
        dataPool.setOrganizationId(createDataPool.getOrganizationId());
        dataPool.setCountries(new Gson().toJson(countries));
        dataPool.setLoadDelay(createDataPool.getLoadDelay());
        dataPool.setPayloadSize(createDataPool.getPayloadSize());

        System.out.println("CreateDataPool: " + createDataPool.toString());
        System.out.println("DataPool:" + dataPool.toString() );


        if(dataPoolService.findByDataVersion(dataPool.getDataVersion()).isPresent()) {
            //DataPool with this Version already exists, throw Exception
            throw new DataPoolExistsException("DataPool with dataVersion " + dataPool.getDataVersion() + " exists already" );
        }

        dataPool = dataPoolService.save(dataPool);

        dataPoolStatusService.saveTargetStatus(new TargetStatus(dataPool.getDataVersion(), LoadStatus.UNLOADED));

        return "redirect:/";
    }

    @GetMapping(path = "/datapool/add/{dataPoolId}")
    public ResponseEntity<?> addDefaultDataPool(@PathVariable String dataPoolId) {
        int parsedDataPoolId = Integer.parseInt(dataPoolId);
        int[] countries = new int[4];

        for (int i = 0; i < 4; i++) {
            countries[i] = parsedDataPoolId + i;
        }

        DataPool dataPool = new DataPool();
        dataPool.setDataVersion((long)parsedDataPoolId);
        dataPool.setProductStructureId(parsedDataPoolId);
        dataPool.setOrganizationId(parsedDataPoolId);
        dataPool.setCountries(new Gson().toJson(countries));
        dataPool.setLoadDelay(1);
        dataPool.setPayloadSize(10000000);

        dataPool = dataPoolService.save(dataPool);

        dataPoolStatusService.saveTargetStatus(new TargetStatus(dataPool.getDataVersion(), LoadStatus.UNLOADED));

        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(path = "/datapool/add")
    public String addDataPool(Model model) {
        model.addAttribute("datapool", new CreateDataPool());

        return "adddatapool";
    }

    @GetMapping(path = "/datapool/delete/{dataPoolId}")
    public String addDataPool(@PathVariable String dataPoolId) {

        dataPoolStatusService.deleteByDataVersion(Long.parseLong(dataPoolId));
        dataPoolService.deleteByDataVersion(Long.parseLong(dataPoolId));

        return "redirect:/";
    }

    //Thrown when Datapool exists while creating new DataPool
    private class DataPoolExistsException extends Exception {
        public DataPoolExistsException(String message) {
            super(message);
        }
        public DataPoolExistsException() {
            super();
        }
    }
}
