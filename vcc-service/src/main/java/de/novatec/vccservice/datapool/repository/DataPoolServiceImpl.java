package de.novatec.vccservice.datapool.repository;

import de.novatec.vccservice.datapool.entity.DataPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataPoolServiceImpl implements DataPoolService {

    @Autowired
    private DataPoolRepository dataPoolRepository;

    @Override
    public Optional<DataPool> findByDataVersion(Long dataVersion) {
        return dataPoolRepository.findByDataVersion(dataVersion);
    }
}
