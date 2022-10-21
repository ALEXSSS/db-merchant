package db.merchant.signal;

import db.merchant.signal.executor.SignalExecutor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

// todo add comment
@Primary
@Component
public class CompositeSignalHandler implements DomainSpecificSignalHandler, InitializingBean {

    private final List<DomainSpecificSignalHandler> handlers;

    private volatile Set<Integer> eligibleSignalIds;

    public CompositeSignalHandler(SignalExecutor signalExecutor,
                                  List<DomainSpecificSignalHandler> handlers) {
        this.handlers = handlers;
        localRefreshSignalHandler();
    }

    @Override
    public void handleSignal(int signal) {
        if (eligibleSignalIds.contains(signal)) {
            SignalExecutor.executor.submit(() -> handlers.stream()
                    .filter(it -> it.getSignalIds().contains(signal))
                    .findFirst().get() // we ensure presence by if above
                    .handleSignal(signal)
            );
        } else {
            // todo rewrite without abstraction leak
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unsupported signal");
        }
    }

    @Override
    public Set<Integer> getSignalIds() {
        return eligibleSignalIds;
    }

    @Override
    public void afterPropertiesSet() {
        // refresh all nested SignalHandlers
        Executors.newSingleThreadScheduledExecutor(
                (runnable) -> new Thread(runnable, "Composite handler refresher")
        ).scheduleWithFixedDelay(this::refreshSignalHandler, 10, 600, TimeUnit.SECONDS);
    }

    @Override
    public void refreshSignalHandler() {
        handlers.forEach(DomainSpecificSignalHandler::refreshSignalHandler);
        localRefreshSignalHandler();
    }

    private void localRefreshSignalHandler() {
        List<Integer> allEligibleSignalIds = handlers.stream()
                .map(DomainSpecificSignalHandler::getSignalIds)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        eligibleSignalIds = new HashSet<>(allEligibleSignalIds);
        if (allEligibleSignalIds.size() != eligibleSignalIds.size()) {
            // remove all cannot be used
            eligibleSignalIds.forEach(allEligibleSignalIds::remove);
            // todo
        }
    }
}
