package de.novatec.vccregistry;

import de.novatec.vccregistry.datapoolmeta.entity.DataPoolMeta;
import de.novatec.vccregistry.datapoolmeta.repository.DataPoolMetaService;
import de.novatec.vccregistry.datapoolstatus.entity.BusinessAttributes;
import de.novatec.vccregistry.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccregistry.datapoolstatus.repository.DataPoolStatusService;
import de.novatec.vccregistry.kubernetes.KubernetesManager;
import de.novatec.vccregistry.resource.GetDataPoolsrequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    public List<DataPool> getDatapoolsByFilter(GetDataPoolsrequest filter) {

        /*
         Bei getDataPools anfrage wird kubernetes DNS nach pods gefragt.
         Dann schaut die ob sie alle Daten im Cache hat oder lädt sie bei bedarf nach.
        */
        List<Long> readyDatapoolIds = kubernetesManager.getLoadedDataPoolIds();

        Predicate<DataPool> nullFilter = dataPool -> dataPool != null;
        Predicate<DataPool> countryFilter = datapool -> filter.getCountryId() == null ? true : datapool.containsCountryId(filter.getCountryId());
        Predicate<DataPool> organizationIdFilter = dataPool -> filter.getOrganizationId() == null ? true : dataPool.getOrganizationId() == filter.getOrganizationId();
        Predicate<DataPool> productStructureIdFilter = dataPool -> filter.getProductStructureId() == null ? true : dataPool.getProductStructureId() == filter.getProductStructureId();

        List<DataPool> dataPools = readyDatapoolIds.stream()
                .map(id -> getOrLoadDataPool(id))
                .filter(nullFilter.and(
                        countryFilter
                        .and(organizationIdFilter
                                .and(productStructureIdFilter))))
                .collect(Collectors.toList());

        return dataPools;
    }

    private DataPool getOrLoadDataPool(Long datapoolId) {

        BusinessAttributes businessAttributes = getOrLoadBusinessAttributes(datapoolId);
        DataPoolMeta dataPoolMeta = getOrLoadMetaData(datapoolId);

        if(businessAttributes == null || dataPoolMeta == null) {
            LOGGER.warn("Deleting orphaned deployment " + datapoolId);
            kubernetesManager.deleteVccServiceDeploymentAndService(datapoolId);
        }

        DataPool datapool = new DataPool();
        datapool.setDataVersion(datapoolId);
        datapool.setCountryIds(businessAttributes.getCountries());
        datapool.setOrganizationId(dataPoolMeta.getOrganizationId());
        datapool.setProductStructureId(dataPoolMeta.getProductStructureId());

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
