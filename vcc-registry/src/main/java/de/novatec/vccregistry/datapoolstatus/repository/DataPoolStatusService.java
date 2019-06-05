package de.novatec.vccregistry.datapoolstatus.repository;

import de.novatec.vccregistry.datapoolmeta.entity.DataPoolMeta;
import de.novatec.vccregistry.datapoolstatus.entity.DataPoolStatus;
import de.novatec.vccregistry.datapoolstatus.entity.TargetStatus;

import java.util.Optional;

public interface DataPoolStatusService {
    Optional<DataPoolStatus> findByDataVersion(Long dataVersion);
    Optional<TargetStatus> findTargetStatusByDataVersion(Long dataVersion);
    void updateTargetStatus(TargetStatus targetStatus);
}
