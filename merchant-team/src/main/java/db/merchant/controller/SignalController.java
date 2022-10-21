package db.merchant.controller;

import db.merchant.signal.SignalHandler;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/signal")
public class SignalController {

    private final SignalHandler signalHandler;

    public SignalController(SignalHandler signalHandler) {
        this.signalHandler = signalHandler;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void handleSignal(@PathVariable Integer id) {
        signalHandler.handleSignal(id);
    }
}
