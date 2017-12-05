package me.laiyijie.job.message.command;

import me.laiyijie.job.message.RunningStatus;

import java.io.Serializable;

/**
 * Created by laiyijie on 11/29/17.
 */
public class JobStatusMsg implements Serializable, RunningStatus {
    private Integer jobId;
    private Integer workFlowId;
    private Integer jobGroupId;
    private String status;

    public Integer getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Integer workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Integer getJobGroupId() {
        return jobGroupId;
    }

    public void setJobGroupId(Integer jobGroupId) {
        this.jobGroupId = jobGroupId;
    }

    public JobStatusMsg(Integer workFlowId, Integer jobGroupId, Integer jobId, String status) {
        this.jobId = jobId;
        this.status = status;
        this.workFlowId = workFlowId;
        this.jobGroupId = jobGroupId;
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
