package de.novatec.vccadmin.datapool.repository;

import de.novatec.vccadmin.datapool.entity.DataPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class DataPoolServiceImpl implements DataPoolService {

    @Autowired
    private DataPoolRepository repository;

    @Override
    public DataPool save(DataPool dataPool) {
        return repository.save(dataPool);
    }

    @Override
    public Optional<DataPool> findByDataVersion(Long dataVersion) {
        return repository.findByDataVersion(dataVersion);
    }

    @Override
    public Iterable<DataPool> findAll() {
        return repository.findAll();
    }

    @Override
    public void deleteByDataVersion(Long dataVersion) {
        repository.deleteById(dataVersion);
    }
}
