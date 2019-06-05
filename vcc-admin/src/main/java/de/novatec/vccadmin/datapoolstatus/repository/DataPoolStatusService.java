package de.novatec.vccadmin.datapoolstatus.repository;

import de.novatec.vccadmin.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccadmin.datapoolstatus.entity.TargetStatus;

import java.util.Optional;

public interface DataPoolStatusService {
    Optional<DataPoolStatus> findByDataVersion(Long dataVersion);
    Optional<TargetStatus> findTargetStatusByDataVersion(Long dataVersion);
    TargetStatus saveTargetStatus(TargetStatus targetStatus);
    void deleteByDataVersion(Long dataVersion);
    void updateTargetStatus(TargetStatus targetStatus);
}
