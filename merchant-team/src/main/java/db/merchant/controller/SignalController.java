package db.merchant.controller;

import db.merchant.signal.DomainSpecificSignalHandlerDispatcher;
import db.merchant.signal.database.AlgoConfiguration;
import db.merchant.signal.database.AlgoConfigurationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleSignal(@PathVariable Integer id) {
        signalHandler.handleSignal(id);
    }

    @GetMapping("/supported")
    public void getAllSupportedSignals() {
        signalHandler.getSignalIds();
    }

    /**
     * for test only, for real life application would require proper authorization,
     * and separation of request/response types
     */
    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public AlgoConfiguration saveAlgoConfiguration(AlgoConfiguration configuration) {
        return repository.save(configuration);
    }
}
