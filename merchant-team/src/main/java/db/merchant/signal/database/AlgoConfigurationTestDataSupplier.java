package db.merchant.signal.database;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Populates database with algo configurations
 */
@Profile("!prod") // just to illustrate that this is for local env
@Component
public class AlgoConfigurationTestDataSupplier implements InitializingBean {

    @Autowired
    private AlgoConfigurationRepository repository;

    @Override
    public void afterPropertiesSet() {
        AlgoConfiguration conf = new AlgoConfiguration();
        AlgoConfiguration.AlgoSteps steps = new AlgoConfiguration.AlgoSteps();
        List<AlgoConfiguration.AlgoStep> stepsList = new ArrayList<>();
        conf.setId(1);
        stepsList.add(new AlgoConfiguration.AlgoStep.DoAlgo());
        stepsList.add(new AlgoConfiguration.AlgoStep.CancelTrades());
        steps.setSteps(stepsList);
        conf.setSteps(steps);
        repository.save(conf);
    }
}
