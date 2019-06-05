package de.novatec.vccservice.datapoolstatus.repository;

import de.novatec.vccservice.datapoolstatus.entity.DataPoolStatus;

import java.util.Optional;

public interface DataPoolStatusService {
    Optional<DataPoolStatus> findByDataVersion(Long dataVersion);
    DataPoolStatus save(DataPoolStatus dataPoolStatus);
}
