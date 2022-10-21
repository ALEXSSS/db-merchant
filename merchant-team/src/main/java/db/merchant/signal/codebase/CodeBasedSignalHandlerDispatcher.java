package db.merchant.signal.codebase;

import db.merchant.signal.DomainSpecificSignalHandlerDispatcher;
import db.merchant.signal.codebase.handlers.AbstractCodebaseSignalHandler;
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
                                System.out.println();
                                // todo log
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
}
