package db.merchant.signal.database;

import db.merchant.signal.database.AlgoConfiguration.AlgoStep;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Populates database with algo configurations for local envs.
 */
@Profile("!prod") // just to illustrate that this is for local env
@Component
public class AlgoConfigurationTestDataSupplier implements InitializingBean {

    @Autowired
    private AlgoConfigurationRepository repository;

    @Override
    public void afterPropertiesSet() {
        AlgoConfiguration configuration = AlgoConfiguration.builder()
                .withId(1)
                .withAuthor("Me")
                .withTask("JIRA_1234")
                .withDescription("Some task from sprint")
                .withStepsArray(new AlgoStep.DoAlgo(), new AlgoStep.CancelTrades())
                .build();
        repository.save(configuration);
    }
}
