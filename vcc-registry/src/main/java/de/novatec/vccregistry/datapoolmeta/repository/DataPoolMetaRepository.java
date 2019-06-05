package de.novatec.vccregistry.datapoolmeta.repository;

import de.novatec.vccregistry.datapoolmeta.entity.DataPoolMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DataPoolMetaRepository extends JpaRepository<DataPoolMeta, Long> {
    Optional<DataPoolMeta> findByDataVersion(Long id);
}
