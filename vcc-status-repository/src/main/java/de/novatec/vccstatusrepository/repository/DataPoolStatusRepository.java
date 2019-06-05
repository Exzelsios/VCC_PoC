package de.novatec.vccstatusrepository.repository;

import de.novatec.vccstatusrepository.entity.DataPoolStatus;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "datapoolstatus", path = "datapoolstatus")
public interface DataPoolStatusRepository extends PagingAndSortingRepository<DataPoolStatus, Long> {

}
