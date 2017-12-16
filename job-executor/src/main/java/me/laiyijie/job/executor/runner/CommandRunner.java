package me.laiyijie.job.executor.runner;

import me.laiyijie.job.executor.service.JobQueueService;
import me.laiyijie.job.message.RunningStatus;
import me.laiyijie.job.message.command.JobStatusMsg;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.log.RunningLogMsg;
import me.laiyijie.job.message.util.OsChecker;
import me.laiyijie.job.message.util.OsType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

/**
 * Created by laiyijie on 11/30/17.
 */
public class CommandRunner implements Runnable {
    private JobQueueService jobQueueService;
    private ConcurrentHashMap<Integer, CommandRunner> runningJobMap;
    private Logger log = LoggerFactory.getLogger(CommandRunner.class);
    private boolean stop;
    private RunJobMsg runJob;
    private Executor executor;
    private String executorName;

    public CommandRunner(RunJobMsg runJob, JobQueueService jobQueueService,
                         ConcurrentHashMap<Integer, CommandRunner> runningJobMap,
                         Executor executor, String clientName) {
        this.runJob = runJob;
        this.stop = false;
        this.jobQueueService = jobQueueService;
        this.runningJobMap = runningJobMap;
        this.executor = executor;
        this.executorName = clientName;
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
            OsType osType = OsChecker.getOsType();
            Process proc = null;
            switch (osType) {
                case Linux:
                    proc = runShellInLinux();
                    break;
                case Windows:
                    proc = runBatInWindows();
                    break;
                case Unknown:
                    log.error("Unknown system");
                    sendCurrentJobStatus(JobStatusMsg.FAILED);
                    return;
            }

            runningJobMap.put(runJob.getJobId(), this);

            collectLog(proc);

            while (proc.isAlive()) {
                if (stop) {
                    proc.destroy();
                    sendCurrentJobStatus(JobStatusMsg.STOPPED);
                    runningJobMap.remove(runJob.getJobId());
                    log.info(" JOB_STOPPED: " + runJob.getJobId());
                    return;
                }
                log.info("proc is alive, job_id : " + runJob.getJobId());
                sendCurrentJobStatus(JobStatusMsg.RUNNING);
                Thread.sleep(1000);
            }
            if (proc.exitValue() == 0) {
                sendCurrentJobStatus(JobStatusMsg.FINISHED);
                runningJobMap.remove(runJob.getJobId());
                log.info(" JOB_FINISHED: " + runJob.getJobId());
            } else {
                log.info(" JOB_FINISHED with exitcode not 0, job_id: " + runJob
                        .getJobId() + " exit code : " + proc.exitValue());
                sendCurrentJobStatus(JobStatusMsg.FAILED);
                runningJobMap.remove(runJob.getJobId());
            }
            jobQueueService.sendLog(new RunningLogMsg(runJob.getWorkFlowId(), runJob.getJobGroupId(),
                    runJob.getJobId(), "exit_code: " + proc.exitValue(), true, System.currentTimeMillis(), executorName));

        } catch (IOException | InterruptedException ex) {
            log.error("error in job_id : " + runJob.getJobId() + " job_command:  " + runJob
                    .getJobCommand());
            log.error(getTrace(ex));
            sendCurrentJobStatus(JobStatusMsg.FAILED);
            runningJobMap.remove(runJob.getJobId());
        }
    }

    private Process runShellInLinux() throws IOException, InterruptedException {
        String tmpFileName = "/tmp/job" + System.currentTimeMillis() + ".sh";
        PrintWriter writer = new PrintWriter(tmpFileName, "UTF-8");
        writer.println(runJob.getJobCommand());
        writer.close();
        Runtime.getRuntime().exec("chmod 777 " + tmpFileName).waitFor();
        ProcessBuilder pb = new ProcessBuilder(tmpFileName);
        return pb.start();
    }

    private Process runBatInWindows() throws IOException, InterruptedException {
        String tmpFileName = "job" + System.currentTimeMillis() + ".bat";
        PrintWriter writer = new PrintWriter(tmpFileName, "UTF-8");
        writer.println(runJob.getJobCommand());
        writer.close();
        ProcessBuilder pb = new ProcessBuilder(tmpFileName);
        return pb.start();
    }

    private void collectLog(Process proc) {
        executor.execute(() -> {
            try {
                BufferedReader stdInput = new BufferedReader(new
                        InputStreamReader(proc.getInputStream()));
                String s;
                while ((s = stdInput.readLine()) != null) {
                    jobQueueService.sendLog(
                            new RunningLogMsg(runJob.getWorkFlowId(), runJob.getJobGroupId(),
                                    runJob.getJobId(), s, false, System.currentTimeMillis(), executorName));
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
                    jobQueueService.sendLog(
                            new RunningLogMsg(runJob.getWorkFlowId(), runJob.getJobGroupId(),
                                    runJob.getJobId(), s, true, System.currentTimeMillis(), executorName));
                    log.info(s);
                }
            } catch (IOException ex) {
                log.error("in ERRORLOG JOB_ID:" + runJob.getJobId() + getTrace(ex));
            }
            log.info("stdError thread leave job_id: " + runJob.getJobId());
        });
    }


    private void sendCurrentJobStatus(String status) {
        jobQueueService.sendJobStatus(
                new JobStatusMsg(runJob.getWorkFlowId(), runJob.getJobGroupId(),
                        runJob.getJobId(), status));
    }

    private String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }
}
