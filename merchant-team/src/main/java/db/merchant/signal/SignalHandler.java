package db.merchant.signal;

/**
 * This is an up-call from our trading system, and we cannot change it.
 * <p>
 * My thoughts:
 * I placed it in the merchant-team artifact, as it seems to fit here better, as
 * the place where signal processing really happens.
 * <p>
 * as both artefacts have their own simple small tasks,
 * based on single responsibility principle:
 * 1) algo-team - provides us with algo implementation
 * 2) merchant-team - process signals
 */
public interface SignalHandler {
    void handleSignal(int signal);
}