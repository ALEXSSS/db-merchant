package db.merchant.controller;

import db.merchant.signal.SignalHandler;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    @Autowired
    private final SignalHandler signalHandler;

    public SignalController(SignalHandler signalHandler) {
        this.signalHandler = signalHandler;
    }

    @Operation(description = "Runs algorithm's handler by signal id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleSignal(@PathVariable int id) {
        signalHandler.handleSignal(id);
    }
}
