package payara;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.*;
import javax.enterprise.concurrent.ManagedExecutorService;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;

/**
 * Starts a Job inside a timer.
 */
@Singleton
@Startup
public class TimedOperator {

    @Resource
    TimerService timers;

    @Resource
    ManagedExecutorService executorService;

    @PostConstruct
    public void init() {
        timers.createTimer(0L,10_000L, null);
    }

    @Timeout
    @SuppressWarnings("unused")
    public void startInTimeout(Timer timer) {
        if(Boolean.parseBoolean(System.getenv("USE_WORKAROUND"))) {
            System.out.println("TimedOperator: starting job with executorService...");
            CompletableFuture.runAsync(this::startJob, executorService);
        } else {
            System.out.println("TimedOperator: starting job...");
        }
    }

    private void startJob() {
        JobOperator operator = BatchRuntime.getJobOperator();
        operator.start("TEST_JOB", new Properties());
    }
}