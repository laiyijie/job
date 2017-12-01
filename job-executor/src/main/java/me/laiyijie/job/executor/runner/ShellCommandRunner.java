package me.laiyijie.job.executor.runner;

import me.laiyijie.job.executor.service.JobQueueService;
import me.laiyijie.job.message.command.JobStatusMsg;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.log.RunningLogMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * Created by laiyijie on 11/30/17.
 */
public class ShellCommandRunner implements Runnable {
    private JobQueueService jobQueueService;
    private ConcurrentHashMap<Integer, ShellCommandRunner> runningJobMap;
    private Logger log = LoggerFactory.getLogger(ShellCommandRunner.class);
    private boolean stop;
    private RunJobMsg runJob;
    private Executor executor;

    public ShellCommandRunner(RunJobMsg runJob, JobQueueService jobQueueService, ConcurrentHashMap<Integer, ShellCommandRunner> runningJobMap, Executor executor) {
        this.runJob = runJob;
        this.stop = false;
        this.jobQueueService = jobQueueService;
        this.runningJobMap = runningJobMap;
        this.executor = executor;
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    @Override
    public void run() {
        try {
            if (runningJobMap.containsKey(runJob.getJobId())) {
                log.error("running two same job in the same time : " + runJob.getJobId());
                return;
            }
            runningJobMap.put(runJob.getJobId(), this);

            String tmpFileName = "/tmp/job" + System.currentTimeMillis() + ".sh";
            PrintWriter writer = new PrintWriter(tmpFileName, "UTF-8");
            writer.println(runJob.getJobCommand());
            writer.close();
            Runtime.getRuntime().exec("chmod 777 " + tmpFileName).waitFor();
            ProcessBuilder pb = new ProcessBuilder(tmpFileName);
            Process proc = pb.start();
            executor.execute(() -> {
                try {
                    BufferedReader stdInput = new BufferedReader(new
                            InputStreamReader(proc.getInputStream()));
                    String s;
                    while ((s = stdInput.readLine()) != null) {
                        jobQueueService.sendLog(new RunningLogMsg(runJob.getJobId(), s, false));
                        log.info(s);
                    }
                } catch (IOException ex) {
                    log.error("in NORMLOG JOB_ID:" + runJob.getJobId() + getTrace(ex));
                }
                log.info("stdOut thread leave job_id: " + runJob.getJobId());
            });
            executor.execute(() -> {
                try {
                    BufferedReader stdError = new BufferedReader(new
                            InputStreamReader(proc.getErrorStream()));
                    String s;
                    while ((s = stdError.readLine()) != null) {
                        jobQueueService.sendLog(new RunningLogMsg(runJob.getJobId(), s, true));
                        log.info(s);
                    }
                } catch (IOException ex) {
                    log.error("in ERRORLOG JOB_ID:" + runJob.getJobId() + getTrace(ex));
                }
                log.info("stdError thread leave job_id: " + runJob.getJobId());
            });

            while (proc.isAlive()) {
                if (stop) {
                    proc.destroy();
                    jobQueueService.sendJobStatus(new JobStatusMsg(runJob.getJobId(), JobStatusMsg.STOPPED));
                    runningJobMap.remove(runJob.getJobId());
                    log.info(" JOB_STOPPED: " + runJob.getJobId());
                    return;
                }
                log.info("proc is alive, job_id : " + runJob.getJobId());
                jobQueueService.sendJobStatus(new JobStatusMsg(runJob.getJobId(), JobStatusMsg.RUNNING));
                Thread.sleep(1000);
            }
            if (proc.exitValue() == 0) {
                jobQueueService.sendJobStatus(new JobStatusMsg(runJob.getJobId(), JobStatusMsg.FINISHED));
                runningJobMap.remove(runJob.getJobId());
                log.info(" JOB_FINISHED: " + runJob.getJobId());
            } else {
                log.info(" JOB_FINISHED with exitcode not 0, job_id: " + runJob.getJobId() + " exit code : " + proc.exitValue());
                jobQueueService.sendJobStatus(new JobStatusMsg(runJob.getJobId(), JobStatusMsg.FAILED));
                runningJobMap.remove(runJob.getJobId());
            }


        } catch (IOException | InterruptedException ex) {
            log.error("error in job_id : " + runJob.getJobId() + " job_command:  " + runJob.getJobCommand());
            log.error(getTrace(ex));
            jobQueueService.sendJobStatus(new JobStatusMsg(runJob.getJobId(), JobStatusMsg.FAILED));
            runningJobMap.remove(runJob.getJobId());
        }
    }

    private String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
