package db.merchant.signal.executor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PreDestroy;
import java.util.concurrent.*;

/**
 * Signal executor, for sake of unification placed in spring container, but
 * due to static nature can be used without injection.
 */
@EnableConfigurationProperties(SignalExecutorProperties.class)
@Component
public class SignalExecutor {

    public final ExecutorService executor;

    public SignalExecutor(SignalExecutorProperties properties) {
        // assuming that signal processing is mostly a CPU bounded task
        ThreadPoolExecutor pool = new ThreadPoolExecutor(
                properties.minThreads,
                properties.maxThreads,
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(properties.queueCapacity),
                runnable -> new Thread(runnable, "Signal processing thread"));
        pool.setRejectedExecutionHandler(new LogAndReportStatusPolicy());
        executor = pool;
    }

    private static class LogAndReportStatusPolicy implements RejectedExecutionHandler {

        public LogAndReportStatusPolicy() {
        }

        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            // if queue is depleted we reject newly arriving request with proper status code
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS);
        }
    }

    // todo log
    @PreDestroy
    public void destroy() {
        executor.shutdown();
        boolean isTerminated = false;
        try {
            isTerminated = executor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            // ignore
        } finally {
            if (!isTerminated) executor.shutdownNow();
        }
    }
}
