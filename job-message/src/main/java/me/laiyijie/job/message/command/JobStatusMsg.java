package me.laiyijie.job.message.command;

import me.laiyijie.job.message.RunningStatus;

import java.io.Serializable;

/**
 * Created by laiyijie on 11/29/17.
 */
public class JobStatusMsg implements Serializable,RunningStatus{
    private Integer jobId;
    private String status;

    public JobStatusMsg(Integer jobId, String status){
        this.jobId = jobId;
        this.status = status;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "JobStatusMsg{" +
                "jobId=" + jobId +
                ", status='" + status + '\'' +
                '}';
    }
}
