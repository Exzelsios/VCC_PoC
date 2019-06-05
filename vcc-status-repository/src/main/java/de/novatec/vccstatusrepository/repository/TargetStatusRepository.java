package de.novatec.vccstatusrepository.repository;

import de.novatec.vccstatusrepository.entity.TargetStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "targetstatus", path = "targetstatus")
public interface TargetStatusRepository extends PagingAndSortingRepository<TargetStatus, Long> {

}
