package payara;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import java.util.Properties;

/**
 * Somehow when executing the job from within an @{@link Asynchronous}, it works.
 */
@Stateless
public class AsyncOperator {

    @Asynchronous
    public void startJob() {
        JobOperator operator = BatchRuntime.getJobOperator();
        operator.start("TEST_JOB", new Properties());
    }
}
