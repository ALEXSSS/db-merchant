package db.merchant.controller;

import db.merchant.signal.DomainSpecificSignalHandlerDispatcher;
import db.merchant.signal.database.AlgoConfiguration;
import db.merchant.signal.database.AlgoConfigurationRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * for tests only, for real life application would require proper authorization,
 * and separation of request/response dto types
 */
@Profile("!prod") // to illustrate that this is not for prod
@RestController
@RequestMapping("/api/signal")
public class DebugController {

    private final AlgoConfigurationRepository repository;

    private final DomainSpecificSignalHandlerDispatcher dispatcher;

    public DebugController(
            DomainSpecificSignalHandlerDispatcher dispatcher,
            AlgoConfigurationRepository repository
    ) {
        this.dispatcher = dispatcher;
        this.repository = repository;
    }

    @Operation(description = "Saves new algo configuration")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AlgoConfiguration saveAlgoConfiguration(@RequestBody AlgoConfiguration configuration) {
        AlgoConfiguration newConf = repository.save(configuration);
        dispatcher.refreshSignalHandler();
        return newConf;
    }

    @Operation(description = "Deletes an algo configuration from database")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteAlgoConfiguration(@PathVariable int id) {
        repository.deleteById(id);
        dispatcher.refreshSignalHandler();
    }

    @Operation(description = "Returns all supported signals' ids")
    @GetMapping("/supported")
    public Set<Integer> getAllSupportedSignals() {
        return dispatcher.getSignalIds();
    }
}
