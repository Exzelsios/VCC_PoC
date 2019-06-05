package de.novatec.vccstatusmanager.repository;

import de.novatec.vccstatusmanager.entity.DataPoolStatus;
import de.novatec.vccstatusmanager.entity.TargetStatus;

import java.util.List;
import java.util.Optional;

public interface DataPoolStatusService {
    Optional<DataPoolStatus> findByDataVersion(Long dataVersion);
    Optional<TargetStatus> findTargetStatusByDataVersion(Long dataVersion);
    List<TargetStatus> findAll();
    void updateTargetStatus(TargetStatus targetStatus);
}
