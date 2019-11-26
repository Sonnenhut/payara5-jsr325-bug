package payara;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.*;
import javax.inject.Inject;
import java.util.Properties;

/**
 * Starts a Job inside a timer.
 */
@Singleton
@Startup
public class TimedOperator {

    @Resource
    TimerService timers;

    @Inject
    AsyncOperator async;

    @PostConstruct
    public void init() {
        timers.createTimer(0L,10_000L, null);
    }

    @Timeout
    @SuppressWarnings("unused")
    public void startInTimeout(Timer timer) {
        if(Boolean.parseBoolean(System.getenv("USE_WORKAROUND"))) {
            async.startJob();
        } else {
            System.out.println("TimedOperator: starting job...");
            JobOperator operator = BatchRuntime.getJobOperator();
            operator.start("TEST_JOB", new Properties());
        }
    }
}