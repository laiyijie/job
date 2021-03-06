package me.laiyijie.job.admin.dao.entity;

import javax.persistence.*;

/**
 * Created by laiyijie on 11/27/17.
 */
@Entity
@Table(name = "job")
public class TbJob {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String description;
    private String status;
    private String script;

    private Long lastRunningBeatTime;

    private String retryRegex;
    private Integer retryTimes;
    private Integer maxRetryTimes;
    private Boolean retryFlag;

    private Boolean ruleRetryFlag;
    private Integer ruleRetryTimes;
    private Integer ruleMaxRetryTimes;

    private String algorithm;

    @ManyToOne
    @JoinColumn(name = "job_group_id")
    private TbJobGroup jobGroup;
    @ManyToOne
    @JoinColumn(name = "executor_group_name")
    private TbExecutorGroup executorGroup;
    @ManyToOne
    @JoinColumn(name = "current_executor_name")
    private TbExecutor currentExecutor;

    public TbExecutor getCurrentExecutor() {
        return currentExecutor;
    }

    public void setCurrentExecutor(TbExecutor currentExecutor) {
        this.currentExecutor = currentExecutor;
    }

    public TbExecutorGroup getExecutorGroup() {
        return executorGroup;
    }

    public Long getLastRunningBeatTime() {
        return lastRunningBeatTime;
    }

    public void setLastRunningBeatTime(Long lastRunningBeatTime) {
        this.lastRunningBeatTime = lastRunningBeatTime;
    }

    public void setExecutorGroup(TbExecutorGroup executorGroup) {
        this.executorGroup = executorGroup;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TbJobGroup getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(TbJobGroup jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getRetryRegex() {
        return retryRegex;
    }

    public void setRetryRegex(String retryRegex) {
        this.retryRegex = retryRegex;
    }

    public Integer getRetryTimes() {
        return retryTimes;
    }

    public void setRetryTimes(Integer retryTimes) {
        this.retryTimes = retryTimes;
    }

    public Integer getMaxRetryTimes() {
        return maxRetryTimes;
    }

    public void setMaxRetryTimes(Integer maxRetryTimes) {
        this.maxRetryTimes = maxRetryTimes;
    }

    public Boolean getRetryFlag() {
        return retryFlag;
    }

    public void setRetryFlag(Boolean retryFlag) {
        this.retryFlag = retryFlag;
    }

    public Boolean getRuleRetryFlag() {
        return ruleRetryFlag;
    }

    public void setRuleRetryFlag(Boolean ruleRetryFlag) {
        this.ruleRetryFlag = ruleRetryFlag;
    }

    public Integer getRuleRetryTimes() {
        return ruleRetryTimes;
    }

    public void setRuleRetryTimes(Integer ruleRetryTimes) {
        this.ruleRetryTimes = ruleRetryTimes;
    }

    public Integer getRuleMaxRetryTimes() {
        return ruleMaxRetryTimes;
    }

    public void setRuleMaxRetryTimes(Integer ruleMaxRetryTimes) {
        this.ruleMaxRetryTimes = ruleMaxRetryTimes;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    @Override
    public String toString() {
        return "TbJob{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", script='" + script + '\'' +
                ", lastRunningBeatTime=" + lastRunningBeatTime +
                ", retryRegex='" + retryRegex + '\'' +
                ", retryTimes=" + retryTimes +
                ", maxRetryTimes=" + maxRetryTimes +
                ", retryFlag=" + retryFlag +
                ", ruleRetryFlag=" + ruleRetryFlag +
                ", ruleRetryTimes=" + ruleRetryTimes +
                ", ruleMaxRetryTimes=" + ruleMaxRetryTimes +
                ", algorithm='" + algorithm + '\'' +
                ", jobGroup=" + jobGroup +
                ", executorGroup=" + executorGroup +
                ", currentExecutor=" + currentExecutor +
                '}';
    }
}
