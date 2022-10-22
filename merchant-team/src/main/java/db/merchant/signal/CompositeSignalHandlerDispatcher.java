package db.merchant.signal;

import db.merchant.signal.executor.SignalExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

/**
 * Combines two strategies for signal processing
 * 1) code based signal processing -
 * when configuration is specified in the codebase (mostly dev-team)
 * 2) database signal processing - when configuration in the database (mostly analysts)
 */
@Primary
@Component
public class CompositeSignalHandlerDispatcher implements DomainSpecificSignalHandlerDispatcher, InitializingBean {

    private final List<DomainSpecificSignalHandlerDispatcher> handlers;

    private volatile Set<Integer> eligibleSignalIds;

    private final SignalExecutor signalExecutor;

    public CompositeSignalHandlerDispatcher(
            SignalExecutor signalExecutor,
            List<DomainSpecificSignalHandlerDispatcher> handlers
    ) {
        this.handlers = handlers;
        this.signalExecutor = signalExecutor;
        localRefreshSignalHandler();
    }

    @Override
    public void handleSignal(int signal) {
        if (eligibleSignalIds.contains(signal)) {
            signalExecutor.executor.submit(() -> handlers.stream()
                    .filter(it -> it.getSignalIds().contains(signal))
                    .findFirst().get() // we ensure presence by if above
                    .handleSignal(signal)
            );
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Unsupported signal id");
        }
    }

    @Override
    public Set<Integer> getSignalIds() {
        return eligibleSignalIds;
    }

    @Override
    public void afterPropertiesSet() {
        // create scheduled task to refresh all nested SignalHandlers
        Executors.newSingleThreadScheduledExecutor(
                (runnable) -> new Thread(runnable, "Composite handler refresher")
        ).scheduleWithFixedDelay(this::refreshSignalHandler, 5, 600, TimeUnit.SECONDS);
    }

    @Override
    public void refreshSignalHandler() {
        // refresh nested handlers
        handlers.forEach(DomainSpecificSignalHandlerDispatcher::refreshSignalHandler);
        localRefreshSignalHandler();
    }

    private void localRefreshSignalHandler() {
        List<Integer> allEligibleSignalIds = handlers.stream()
                .map(DomainSpecificSignalHandlerDispatcher::getSignalIds)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        eligibleSignalIds = new HashSet<>(allEligibleSignalIds);
        if (allEligibleSignalIds.size() != eligibleSignalIds.size()) {
            // removeAll() cannot be used
            eligibleSignalIds.forEach(allEligibleSignalIds::remove);
            if (!allEligibleSignalIds.isEmpty()) {
                log.error("Check ids, as there are repetitions " + allEligibleSignalIds);
            }
        }
    }

    private static final Logger log = LoggerFactory.getLogger(CompositeSignalHandlerDispatcher.class);
}
