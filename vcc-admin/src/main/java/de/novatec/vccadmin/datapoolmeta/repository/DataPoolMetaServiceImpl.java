package de.novatec.vccadmin.datapoolmeta.repository;

import de.novatec.vccadmin.datapoolmeta.entity.DataPoolMeta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DataPoolMetaServiceImpl implements DataPoolMetaService {

    @Autowired
    private DataPoolMetaRepository repository;

    @Override
    public DataPoolMeta save(DataPoolMeta dataPool) {
        return repository.save(dataPool);
    }

    @Override
    public Optional<DataPoolMeta> findByDataVersion(Long dataVersion) {
        return repository.findByDataVersion(dataVersion);
    }

    @Override
    public List<DataPoolMeta.DataPoolMetaId> getAllIds() {
        return repository.findAllByOrderByDataVersionAsc();
    }
}
