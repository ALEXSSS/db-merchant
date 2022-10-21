package db.merchant.signal.codebase.handlers.jira_0001;

import db.merchant.signal.codebase.handlers.AbstractCodebaseSignalHandler;

/**
 * Example of a simple code-based handler
 */
public class SomeSpecificCaseHandler extends AbstractCodebaseSignalHandler {

    @Override
    public void handleSignal() {
        algo.setAlgoParam(100, 500);
        algo.doAlgo();
        algo.cancelTrades();
    }

    @Override
    public int getSignalId() {
        return 100500;
    }
}