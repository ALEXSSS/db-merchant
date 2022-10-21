package db.merchant.signal.executor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@ConfigurationProperties("db.merchant.executor")
@ConstructorBinding
public class SignalExecutorProperties {

    public final int minThreads;
    public final int maxThreads;
    public final int queueCapacity;

    private static int QUEUE_DEFAULT_CAPACITY;

    public SignalExecutorProperties(Integer minThreads,
                                    Integer maxThreads,
                                    Integer queueCapacity) {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        minThreads = (minThreads == null) ? availableProcessors : minThreads;
        maxThreads = (maxThreads == null) ? availableProcessors : maxThreads;
        queueCapacity = (queueCapacity == null) ? QUEUE_DEFAULT_CAPACITY : queueCapacity;
        if (minThreads > maxThreads) {
            throw new IllegalStateException(
                    "minThreads cannot be greater than maxThreads, " +
                            "minThreads: " + minThreads + ", " +
                            "maxThreads: " + maxThreads);
        }
        if (minThreads < 0 || queueCapacity < 0) {
            throw new IllegalStateException(
                    "minThreads, maxThreads, queueCapacity must be greater than 0, " +
                            "minThreads: " + minThreads + ", " +
                            "maxThreads: " + maxThreads + ", " +
                            "queueCapacity: " + queueCapacity);
        }
        this.minThreads = minThreads;
        this.maxThreads = maxThreads;
        this.queueCapacity = queueCapacity;
    }
}