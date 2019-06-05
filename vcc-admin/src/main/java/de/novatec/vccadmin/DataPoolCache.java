package de.novatec.vccadmin;

import de.novatec.vccadmin.datapool.entity.ViewDataPool;
import de.novatec.vccadmin.datapoolmeta.entity.DataPoolMeta;
import de.novatec.vccadmin.datapoolmeta.repository.DataPoolMetaService;
import de.novatec.vccadmin.datapoolstatus.entity.BusinessAttributes;
import de.novatec.vccadmin.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccadmin.datapoolstatus.entity.LoadStatus;
import de.novatec.vccadmin.datapoolstatus.entity.TargetStatus;
import de.novatec.vccadmin.datapoolstatus.repository.DataPoolStatusService;
import de.novatec.vccadmin.kubernetes.KubernetesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class DataPoolCache {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataPoolCache.class);

    @Autowired
    private DataPoolStatusService dataPoolStatusService;

    @Autowired
    private DataPoolMetaService dataPoolMetaService;

    @Autowired
    private KubernetesManager kubernetesManager;

    private Map<Long, BusinessAttributes> businessAttributesMap = new HashMap<>();
    private Map<Long, DataPoolMeta> metadataMap = new HashMap<>();

    public List<ViewDataPool> getAllDataPools() {
        Predicate<ViewDataPool> nullFilter = dataPool -> dataPool != null;

        List<DataPoolMeta.DataPoolMetaId> allDataPoolIds = dataPoolMetaService.getAllIds();

        List<ViewDataPool> dataPools = allDataPoolIds.stream()
                .map(id -> getOrLoadDataPool(id.getDataVersion()))
                .filter(dataPool -> dataPool != null)
                .collect(Collectors.toList());

        return dataPools;
    }

    private ViewDataPool getOrLoadDataPool(Long datapoolId) {

        // get metadata and business attributes from cache or load them from db
        BusinessAttributes businessAttributes = getOrLoadBusinessAttributes(datapoolId);
        DataPoolMeta dataPoolMeta = getOrLoadMetaData(datapoolId);

        // get targetstatus from vcc-status-repository
        Optional<TargetStatus> targetStatus = dataPoolStatusService.findTargetStatusByDataVersion(datapoolId);

        // list id's of all loaded datapool deployments
        List<Long> readyDatapoolIds = kubernetesManager.getLoadedDataPoolIds();
        LOGGER.info("Loaded datapoolIds: " + readyDatapoolIds.toString());

        if(dataPoolMeta == null) {
            return null;
        }

        ViewDataPool datapool = new ViewDataPool(
                datapoolId,
                dataPoolMeta.getOrganizationId(),
                dataPoolMeta.getProductStructureId(),
                dataPoolMeta.getPayloadSize(),
                dataPoolMeta.getLoadDelay(),
                targetStatus.isPresent() ? targetStatus.get().getTargetStatus() : LoadStatus.UNDEFINED,
                readyDatapoolIds.contains(datapoolId) ? LoadStatus.LOADED : LoadStatus.UNLOADED,
                businessAttributes);

        return datapool;
    }

    private BusinessAttributes getOrLoadBusinessAttributes(Long datapoolId) {
        if(!businessAttributesMap.containsKey(datapoolId)) {
            Optional<DataPoolStatus> dataPoolStatus = dataPoolStatusService.findByDataVersion(datapoolId);

            if(!dataPoolStatus.isPresent()) {
                LOGGER.warn("Deployed datapool " + datapoolId + " not present in vcc-status.");
                return null;
            }

            businessAttributesMap.put(datapoolId, dataPoolStatus.get().getBusinessAttributes());
        }

        return businessAttributesMap.get(datapoolId);
    }

    private DataPoolMeta getOrLoadMetaData(Long datapoolId) {
        if(!metadataMap.containsKey(datapoolId)) {
            Optional<DataPoolMeta> dataPoolMeta = dataPoolMetaService.findByDataVersion(datapoolId);

            if(!dataPoolMeta.isPresent()) {
                LOGGER.warn("Deployed datapool " + datapoolId + " not present in vcc-pool.");
                return null;
            }

            metadataMap.put(datapoolId, dataPoolMeta.get());
        }

        return metadataMap.get(datapoolId);
    }
}
