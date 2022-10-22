package db.merchant.signal.codebase;

import db.merchant.signal.DomainSpecificSignalHandlerDispatcher;
import db.merchant.signal.codebase.handlers.AbstractCodebaseSignalHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

/**
 * Collects all dev team defined handlers, and dispatches signals among them
 */
@Component
public class CodeBasedSignalHandlerDispatcher implements DomainSpecificSignalHandlerDispatcher {

    private final Map<Integer, AbstractCodebaseSignalHandler> handlers;

    public CodeBasedSignalHandlerDispatcher(List<AbstractCodebaseSignalHandler> handlers) {
        // check that there are no repetitions of signal ids, during bootstrap of app
        this.handlers = handlers.stream()
                .collect(groupingBy(AbstractCodebaseSignalHandler::getSignalId))
                .entrySet().stream()
                .peek(
                        entry -> {
                            if (entry.getValue().size() != 1) {
                                List<AbstractCodebaseSignalHandler> ids = entry.getValue();
                                String implementations = ids.stream().map(AbstractCodebaseSignalHandler::getTask)
                                        .collect(Collectors.joining(","));
                                log.error("Signal ids must be unique, but for " + ids.get(0) +
                                        " got " + ids.size() + " handlers, in implementations: " + implementations);
                                throw new IllegalStateException(
                                        "All code defined algo configurations must have unique id!"
                                );
                            }
                        }
                ).collect(Collectors.toMap(Map.Entry::getKey, it -> it.getValue().get(0)));
    }

    @Override
    public void handleSignal(int signal) {
        handlers.get(signal).handleSignal();
    }

    @Override
    public Set<Integer> getSignalIds() {
        return handlers.keySet();
    }

    private static final Logger log = LoggerFactory.getLogger(CodeBasedSignalHandlerDispatcher.class);
}
