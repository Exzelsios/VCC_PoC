package de.novatec.vccclient.datapool.repository;

import de.novatec.vccclient.datapool.entity.DataPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataPoolServiceImpl implements DataPoolService {

    @Autowired
    private DataPoolRepository repository;

    @Override
    public Optional<DataPool> findByDataVersion(Long dataVersion) {
        return repository.findByDataVersion(dataVersion);
    }

    @Override
    public Iterable<DataPool> findAll() {
        return repository.findAll();
    }
}
