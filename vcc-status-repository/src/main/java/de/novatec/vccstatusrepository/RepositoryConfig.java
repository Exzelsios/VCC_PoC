package de.novatec.vccstatusrepository;

import de.novatec.vccstatusrepository.entity.DataPoolStatus;
import de.novatec.vccstatusrepository.entity.TargetStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;

@Configuration
public class RepositoryConfig extends RepositoryRestConfigurerAdapter {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(DataPoolStatus.class);
        config.exposeIdsFor(TargetStatus.class);
    }
}