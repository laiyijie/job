package me.laiyijie.job.admin.dao.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by laiyijie on 12/16/17.
 */
@Entity
@Table(name = "job_error_log")
public class TbJobErrorLog {
    @Id
    @GeneratedValue
    private Long id;
    private Integer workflowId;
    private Integer jobGroupId;
    private Integer jobId;
    private String content;
    private Long logTime;
    private String executorName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getWorkflowId() {
        return workflowId;
    }

    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLogTime() {
        return logTime;
    }

    public void setLogTime(Long logTime) {
        this.logTime = logTime;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    @Override
    public String toString() {
        return "TbJobErrorLog{" +
                "id=" + id +
                ", workflowId=" + workflowId +
                ", jobGroupId=" + jobGroupId +
                ", jobId=" + jobId +
                ", content='" + content + '\'' +
                ", logTime=" + logTime +
                ", executorName='" + executorName + '\'' +
                '}';
    }
}
