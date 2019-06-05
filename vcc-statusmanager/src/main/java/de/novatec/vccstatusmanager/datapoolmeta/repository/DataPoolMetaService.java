package de.novatec.vccstatusmanager.datapoolmeta.repository;

import de.novatec.vccstatusmanager.datapoolmeta.entity.DataPoolMeta;

import java.util.Optional;

public interface DataPoolMetaService {
    DataPoolMeta save(DataPoolMeta dataPool);
    Optional<DataPoolMeta> findByDataVersion(Long dataVersion);
}
