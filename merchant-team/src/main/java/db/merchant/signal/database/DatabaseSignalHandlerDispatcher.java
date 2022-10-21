package db.merchant.signal.database;

import db.algo.Algo;
import db.merchant.signal.DomainSpecificSignalHandlerDispatcher;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Dispatches signal among persisted configurations.
 * <p>
 * This dispatcher is created to support handlers specified mostly by analysts (not dev team).
 */
@Component
public class DatabaseSignalHandlerDispatcher implements DomainSpecificSignalHandlerDispatcher {

    /**
     * after initialization is read-only, therefore it is thread-safe to use it
     */
    private volatile Map<Integer, AlgoConfiguration> configurations = new HashMap<>();
    private final Algo algo;
    private final AlgoConfigurationRepository repository;

    public DatabaseSignalHandlerDispatcher(Algo algo, AlgoConfigurationRepository repository) {
        this.algo = algo;
        this.repository = repository;
    }

    @Override
    public void handleSignal(int signal) {
        runAlgoByDatabaseConfiguration(configurations.get(signal));
    }

    @Override
    public Set<Integer> getSignalIds() {
        return configurations.keySet();
    }

    @Override
    public void refreshSignalHandler() {
        Map<Integer, AlgoConfiguration> configurationsMap = new HashMap<>();
        repository.findAll().forEach((conf) -> configurationsMap.put(conf.getId(), conf));
        configurations = configurationsMap;
    }

    private void runAlgoByDatabaseConfiguration(AlgoConfiguration configuration) {
        configuration.getSteps().getSteps().forEach((step) -> step.handle(algo));
    }
}
