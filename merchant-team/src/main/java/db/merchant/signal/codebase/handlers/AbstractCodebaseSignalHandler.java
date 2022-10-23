package db.merchant.signal.codebase.handlers;

import db.algo.Algo;
import db.merchant.utils.PropagatedComponent;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * SignalHandlers added by dev team
 * <p>
 * By inheritance of this class and putting implementation in the handler folder, bean
 * will be automatically registered by spring container.
 * <p>
 * Other possibilities were to create similar functionality by BPP, but it is unnecessary
 * in the given case.
 */
@PropagatedComponent
public abstract class AbstractCodebaseSignalHandler {

    protected static Algo algo;

    public abstract void handleSignal();

    /**
     * Must be unique among all signal handlers.
     * FUTURE WORK: consider usage of the jira task name (String instead of int)
     *
     * @return unique signal id
     */
    public abstract int getSignalId();

    @Autowired
    private void setAlgo(Algo algo) {
        AbstractCodebaseSignalHandler.algo = algo;
    }

    // some additional info which can be used for monitoring

    /**
     * @return name of the associated jira task
     */
    public String getTask() {
        String[] packageName = this.getClass().getPackageName().split("\\.");
        return packageName[packageName.length - 1];
    }

    /**
     * @return author of this handler
     */
    public String getAuthor() {
        return "NOT SPECIFIED";
    }

    /**
     * @return optional description shortly describing used approach
     */
    public String getDescription() {
        return "NOT SPECIFIED";
    }
}