package db.merchant.signal.codebase.handlers.jira_0000;

import db.merchant.signal.codebase.handlers.AbstractCodebaseSignalHandler;

public class ExampleCodebaseSignalHandler extends AbstractCodebaseSignalHandler {

    @Override
    public void handleSignal() {
        algo.setAlgoParam(1, 2);
        algo.doAlgo();
    }

    @Override
    public int getSignalId() {
        return 0;
    }
}
