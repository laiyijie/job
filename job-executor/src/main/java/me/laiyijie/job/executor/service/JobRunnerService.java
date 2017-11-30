package me.laiyijie.job.executor.service;

import me.laiyijie.job.executor.runner.ShellCommandRunner;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.executor.StopJobMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
public class JobRunnerService {
    @Autowired
    private JobQueueService jobQueueService;
    private Executor executor = Executors.newCachedThreadPool();

    private Logger log = LoggerFactory.getLogger(JobRunnerService.class);
    private ConcurrentHashMap<Integer, ShellCommandRunner> runningJobMap = new ConcurrentHashMap<>();

    public void runShell(RunJobMsg runJob) {
        if (runningJobMap.containsKey(runJob.getJobId())) {
            //TODO need log error
            log.error("the job already run in this executor : " + runJob.getJobId());
            return;
        }
        ShellCommandRunner shellCommandThread = new ShellCommandRunner(runJob, jobQueueService, runningJobMap, executor);
        Thread t = new Thread(shellCommandThread);
        t.start();
    }

    public void stopShell(StopJobMsg stopJobMsg) {
        log.info("enter stop shell job_id: " + stopJobMsg.getJobId());
        if (runningJobMap.containsKey(stopJobMsg.getJobId())) {
            log.info("start stop job: " + stopJobMsg.getJobId());
            runningJobMap.get(stopJobMsg.getJobId()).setStop(true);
        }
    }
}
