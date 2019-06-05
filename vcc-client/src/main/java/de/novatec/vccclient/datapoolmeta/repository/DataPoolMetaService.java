package de.novatec.vccclient.datapoolmeta.repository;

import de.novatec.vccclient.datapoolmeta.entity.DataPoolMeta;

import java.util.List;
import java.util.Optional;

public interface DataPoolMetaService {
    Optional<DataPoolMeta> findByDataVersion(Long dataVersion);
    List<DataPoolMeta.DataPoolMetaId> getAllIds();
}
