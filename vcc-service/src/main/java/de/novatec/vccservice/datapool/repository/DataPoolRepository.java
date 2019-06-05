package de.novatec.vccservice.datapool.repository;

import de.novatec.vccservice.datapool.entity.DataPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataPoolRepository extends JpaRepository<DataPool, Long> {
    Optional<DataPool> findByDataVersion(Long dataVersion);
}
