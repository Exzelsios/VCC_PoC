package de.novatec.vccstatusmanager.datapoolmeta.repository;

import de.novatec.vccstatusmanager.datapoolmeta.entity.DataPoolMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DataPoolMetaRepository extends JpaRepository<DataPoolMeta, Long> {
    Optional<DataPoolMeta> findByDataVersion(Long id);
}
