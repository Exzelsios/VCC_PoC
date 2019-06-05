package de.novatec.vccclient.datapool.repository;

import de.novatec.vccclient.datapool.entity.DataPool;

import java.util.Optional;

public interface DataPoolService {
    Optional<DataPool> findByDataVersion(Long dataVersion);
    Iterable<DataPool> findAll();
}
