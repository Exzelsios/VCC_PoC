package de.novatec.vccclient.datapoolstatus.repository;

import de.novatec.vccclient.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccclient.datapoolstatus.entity.TargetStatus;

import java.util.Optional;

public interface DataPoolStatusService {
    Optional<DataPoolStatus> findByDataVersion(Long dataVersion);
    Optional<TargetStatus> findTargetStatusByDataVersion(Long dataVersion);
    void deleteByDataVersion(Long dataVersion);
}
