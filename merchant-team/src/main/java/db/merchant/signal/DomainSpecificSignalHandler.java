package db.merchant.signal;

import java.util.Set;

public interface DomainSpecificSignalHandler extends SignalHandler {

    // todo
    Set<Integer> getSignalIds();

    default void refreshSignalHandler() {};
}
