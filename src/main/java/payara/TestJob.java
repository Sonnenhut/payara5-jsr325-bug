package payara;

import javax.batch.api.AbstractBatchlet;
import javax.inject.Named;

@Named("TestJob")
public class TestJob extends AbstractBatchlet {
    @Override
    public String process() throws Exception {
        System.out.println("Executed TestJob!!!");
        return "DONE";
    }

}
