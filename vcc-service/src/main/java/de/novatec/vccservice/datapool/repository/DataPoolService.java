package de.novatec.vccservice.datapool.repository;

import de.novatec.vccservice.datapool.entity.DataPool;

import java.util.Optional;

public interface DataPoolService {
    Optional<DataPool> findByDataVersion(Long dataVersion);
}
