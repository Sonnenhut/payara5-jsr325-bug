### Bug with Payara5 running JBatch inside Timer

When running `BatchRuntime.getJobOperator().start()` inside a JEE `@Timeout` function, Exceptions on the log appear and jobs are not being executed.

#### Reproduce this bug

When you have Docker and linux, execute `chmod +x ./build.sh && ./build.sh`.


#### Workaround

Working around this issue seems to be to start the Job from within a `@Asynchronous` function.
Change `USE_WORKAROUND` in the `Dockerfile` to `true` to test it out.


#### Affected versions

This applies to following versions:

 - payara/server-full:5.193.1
 - payara/server-full:5.193
 - payara/server-full:5.192
 - payara/server-full:5.191
 - payara/server-full:5.184
 

These versions are not affected:

 - payara/server-full:5.183
 - payara/server-full:5.182
 - payara/server-full:5.181


#### Occuring exceptions

Following exceptions occur in the log when the bug happens:


    [#|2019-11-26T15:26:31.727+0000|WARNING|Payara 5.193|com.ibm.jbatch.container.impl.JobThreadRootControllerImpl|_ThreadID=178;_ThreadName=concurrent/__defaultManagedExecutorService-managedThreadFactory-Thread-1;_TimeMillis=1574781991727;_L
    evelValue=900;|                                                                                                                                                                                                                               
      Caught throwable in main execution loop with Throwable message: null, and stack trace: java.lang.NullPointerException                                                                                                                       
            at com.ibm.jbatch.container.impl.BaseStepControllerImpl.updateBatchStatus(BaseStepControllerImpl.java:287)                                                                                                                            
            at com.ibm.jbatch.container.impl.BaseStepControllerImpl.markStepFailed(BaseStepControllerImpl.java:243)                                                                                                                               
            at com.ibm.jbatch.container.impl.BaseStepControllerImpl.markJobAndStepFailed(BaseStepControllerImpl.java:248)                                                                                                                         
            at com.ibm.jbatch.container.impl.BaseStepControllerImpl.execute(BaseStepControllerImpl.java:133)                                                                                                                                      
            at com.ibm.jbatch.container.impl.ExecutionTransitioner.doExecutionLoop(ExecutionTransitioner.java:112)                                                                                                                                
            at com.ibm.jbatch.container.impl.JobThreadRootControllerImpl.originateExecutionOnThread(JobThreadRootControllerImpl.java:110)                                                                                                         
            at com.ibm.jbatch.container.util.BatchWorkUnit.run(BatchWorkUnit.java:80)                                                                                                                                                             
            at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)                                                                                                                                                            
            at java.util.concurrent.FutureTask.run(FutureTask.java:266)                                                                                                                                                                           
            at org.glassfish.enterprise.concurrent.internal.ManagedFutureTask.run(ManagedFutureTask.java:143)                                                                                                                                     
            at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)                                                                                                                                                    
            at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)                                                                                                                                                    
            at java.lang.Thread.run(Thread.java:748)                                                                                                                                                                                              
            at org.glassfish.enterprise.concurrent.ManagedThreadFactoryImpl$ManagedThread.run(ManagedThreadFactoryImpl.java:250)                                                                                                                  
    |#]                                                                                                                                                                                                                                           
                                                                                                                                                                                                                                                  
    [#|2019-11-26T15:26:31.735+0000|WARNING|Payara 5.193|com.ibm.jbatch.container.util|_ThreadID=178;_ThreadName=concurrent/__defaultManagedExecutorService-managedThreadFactory-Thread-1;_TimeMillis=1574781991735;_LevelValue=900;|             
      Caught throwable from run().  Stack trace: java.lang.IllegalStateException: Couldn't find entry to update for id = 1                                                                                                                        
            at com.ibm.jbatch.container.services.impl.JobStatusManagerImpl.updateJobBatchStatus(JobStatusManagerImpl.java:82)                                                                                                                     
            at com.ibm.jbatch.container.impl.JobThreadRootControllerImpl.persistJobBatchAndExitStatus(JobThreadRootControllerImpl.java:214)                                                                                                       
            at com.ibm.jbatch.container.impl.JobThreadRootControllerImpl.endOfJob(JobThreadRootControllerImpl.java:200)                                                                                                                           
            at com.ibm.jbatch.container.impl.JobThreadRootControllerImpl.originateExecutionOnThread(JobThreadRootControllerImpl.java:128)                                                                                                         
            at com.ibm.jbatch.container.util.BatchWorkUnit.run(BatchWorkUnit.java:80)                                                                                                                                                             
            at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511)                                                                                                                                                            
            at java.util.concurrent.FutureTask.run(FutureTask.java:266)                                                                                                                                                                           
            at org.glassfish.enterprise.concurrent.internal.ManagedFutureTask.run(ManagedFutureTask.java:143)                                                                                                                                     
            at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)                                                                                                                                                    
            at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)                                                                                                                                                    
            at java.lang.Thread.run(Thread.java:748)                                                                                                                                                                                              
            at org.glassfish.enterprise.concurrent.ManagedThreadFactoryImpl$ManagedThread.run(ManagedThreadFactoryImpl.java:250)                                                                                                                  
    |#]          
           
                                                                                                                                                                                                                                 