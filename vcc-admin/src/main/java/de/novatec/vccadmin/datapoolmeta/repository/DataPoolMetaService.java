package de.novatec.vccadmin.datapoolmeta.repository;

import de.novatec.vccadmin.datapoolmeta.entity.DataPoolMeta;

import java.util.List;
import java.util.Optional;

public interface DataPoolMetaService {
    DataPoolMeta save(DataPoolMeta dataPool);
    Optional<DataPoolMeta> findByDataVersion(Long dataVersion);
    List<DataPoolMeta.DataPoolMetaId> getAllIds();
}
