package de.novatec.vccclient.datapoolmeta.repository;

import de.novatec.vccclient.datapoolmeta.entity.DataPoolMeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataPoolMetaRepository extends JpaRepository<DataPoolMeta, Long> {
    Optional<DataPoolMeta> findByDataVersion(Long id);
    List<DataPoolMeta.DataPoolMetaId> findAllByOrderByDataVersionAsc();
}
