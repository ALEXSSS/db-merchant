package db.merchant.signal.codebase;

import db.merchant.signal.DomainSpecificSignalHandler;
import db.merchant.signal.codebase.handlers.AbstractCodebaseSignalHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

// todo
@Component
public class CodeBasedSignalHandler implements DomainSpecificSignalHandler {

    /**
     * map after initialization is read-only, therefore it is thread-safe to use it
     */
    private final Map<Integer, AbstractCodebaseSignalHandler> handlers;

    public CodeBasedSignalHandler(List<AbstractCodebaseSignalHandler> handlers) {
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
