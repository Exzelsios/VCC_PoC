package de.novatec.vccregistry.datapoolmeta.repository;

import de.novatec.vccregistry.datapoolmeta.entity.DataPoolMeta;

import java.util.Optional;

public interface DataPoolMetaService {
    DataPoolMeta save(DataPoolMeta dataPool);
    Optional<DataPoolMeta> findByDataVersion(Long dataVersion);
}
