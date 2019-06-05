package de.novatec.vccservice;

import com.google.gson.Gson;
import de.novatec.vccservice.datapool.entity.DataPool;
import de.novatec.vccservice.datapool.repository.DataPoolService;
import de.novatec.vccservice.datapoolstatus.entity.BusinessAttributes;
import de.novatec.vccservice.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccservice.datapoolstatus.entity.TargetStatus;
import de.novatec.vccservice.datapoolstatus.repository.DataPoolStatusService;
import de.novatec.vccservice.datapoolstatus.repository.RestDataPoolStatusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataPoolCache {

    private Logger LOGGER = LoggerFactory.getLogger(DataPoolCache.class);

    @Autowired
    private DataPoolService dataPoolService;

    @Autowired
    private DataPoolStatusService dataPoolStatusService;

    @Value("${DATAPOOLID:undefined}")
    private Long dataPoolId;

    private DataPool dataPool;
    private byte[] payload;

    public void initializePayload() {

        Optional<DataPool> dataPoolOptional = dataPoolService.findByDataVersion(dataPoolId);

        if(!dataPoolOptional.isPresent()) {
            throw new RuntimeException("Loading failed. Datapool with id=" + dataPoolId + " not present in pool database.");
        }

        DataPool loadedDataPool = dataPoolOptional.get();

        LOGGER.info("Initializing service instance with datapoolId=" + dataPoolId + " loadDelay=" + loadedDataPool.getLoadDelay() + " payloadSize=" + loadedDataPool.getPayloadSize());

        payload = new byte[loadedDataPool.getPayloadSize()];
        try {
            Thread.sleep(loadedDataPool.getLoadDelay());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("Completed loaddelay. Loading datapool.");



        dataPool = loadedDataPool;

        LOGGER.info("Datapool loading completed.");
        dataPoolStatusService.save(new DataPoolStatus(dataPool.getDataVersion(), TargetStatus.LOADED, new BusinessAttributes(new Gson().fromJson(dataPool.getCountries(), int[].class))));
        LOGGER.info("Sent datapoolstatus to vcc-status.");
    }

    public DataPool getDataPool() {
        return dataPool;
    }

    public boolean isReady() {
        return dataPool != null;
    }
}
