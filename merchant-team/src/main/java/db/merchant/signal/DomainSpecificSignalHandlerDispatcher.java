package db.merchant.signal;

import java.util.Set;

/**
 * Signal handler with its specific working domain defined by signalIds
 */
public interface DomainSpecificSignalHandlerDispatcher extends SignalHandler {

    /**
     * @return signal ids serviced by this handler
     */
    Set<Integer> getSignalIds();

    /**
     * refreshes handler updating it with newly added/removed signal handlers.
     */
    default void refreshSignalHandler() {};
}
