package de.novatec.vccadmin.datapool.repository;

import de.novatec.vccadmin.datapool.entity.DataPool;

import java.util.Optional;

public interface DataPoolService {
    DataPool save(DataPool dataPool);
    Optional<DataPool> findByDataVersion(Long dataVersion);
    Iterable<DataPool> findAll();
    void deleteByDataVersion(Long dataVersion);
}
