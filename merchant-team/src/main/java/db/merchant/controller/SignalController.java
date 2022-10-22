package db.merchant.controller;

import db.merchant.signal.DomainSpecificSignalHandlerDispatcher;
import db.merchant.signal.database.AlgoConfiguration;
import db.merchant.signal.database.AlgoConfigurationRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    private final DomainSpecificSignalHandlerDispatcher signalHandler;
    private final AlgoConfigurationRepository repository;

    public SignalController(
            AlgoConfigurationRepository repository,
            DomainSpecificSignalHandlerDispatcher signalHandler
    ) {
        this.signalHandler = signalHandler;
        this.repository = repository;
    }

    @Operation(description = "Runs algorithm's handler by signal id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleSignal(@PathVariable int id) {
        signalHandler.handleSignal(id);
    }

    /**
     * for test only, for real life application would require proper authorization,
     * and separation of request/response types
     */
    @Operation(description = "Saves new algo configuration")
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlgoConfiguration saveAlgoConfiguration(AlgoConfiguration configuration) {
        return repository.save(configuration);
    }

    @Operation(description = "Returns all supported signals' ids")
    @GetMapping("/supported")
    public Set<Integer> getAllSupportedSignals() {
        return signalHandler.getSignalIds();
    }
}
