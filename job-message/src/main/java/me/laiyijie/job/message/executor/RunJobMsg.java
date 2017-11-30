package me.laiyijie.job.message.executor;

import java.io.Serializable;

/**
 * Created by laiyijie on 11/29/17.
 */
public class RunJobMsg implements Serializable {
    private Integer jobId;

    private String jobCommand;

    public RunJobMsg(Integer jobId, String jobCommand) {
        this.jobCommand = jobCommand;
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobCommand() {
        return jobCommand;
    }

    public void setJobCommand(String jobCommand) {
        this.jobCommand = jobCommand;
    }

    @Override
    public String toString() {
        return "RunJobMsg{" +
                "jobId=" + jobId +
                ", jobCommand='" + jobCommand + '\'' +
                '}';
    }
}
