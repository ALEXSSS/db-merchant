package db.merchant.signal.codebase.handlers;

import db.algo.Algo;
import db.merchant.utils.PropagatedComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SignalHandlers added by dev team
 */
@PropagatedComponent
public abstract class AbstractCodebaseSignalHandler {

    protected static Algo algo;

    public abstract void handleSignal();

    public abstract int getSignalId();

    // todo private
    @Autowired
    public void setAlgo(Algo algo) {
        AbstractCodebaseSignalHandler.algo = algo;
    }

    // some additional info which can be used for monitoring

    // todo
    String getTask() {
        String[] packageName = this.getClass().getPackageName().split("\\.");
        return packageName[packageName.length - 1];
    }

    // todo
    String getAuthor() {
        return "NOT SPECIFIED";
    }

    // todo
    String getDescription() {
        return "NOT SPECIFIED";
    }
}