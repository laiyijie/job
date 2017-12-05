package me.laiyijie.job.message.log;

import java.io.Serializable;

/**
 * Created by laiyijie on 11/29/17.
 */
public class RunningLogMsg implements Serializable {
    private Integer jobId;
    private Integer workFlowId;
    private Integer jobGroupId;
    private Boolean error;
    private String content;

    public RunningLogMsg() {
    }

    public RunningLogMsg(Integer workFlowId, Integer jobGroupId, Integer jobId, String log, Boolean isError) {
        this.content = log;
        this.error = isError;
        this.jobId = jobId;
        this.workFlowId = workFlowId;
        this.jobGroupId = jobGroupId;
    }

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

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RunningLogMsg{" +
                "jobId=" + jobId +
                ", error=" + error +
                ", content='" + content + '\'' +
                '}';
    }
}
