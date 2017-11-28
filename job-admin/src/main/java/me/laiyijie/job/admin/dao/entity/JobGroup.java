package me.laiyijie.job.admin.dao.entity;

import me.laiyijie.job.admin.dao.state.RunningStatus;

import javax.persistence.*;

/**
 * Created by laiyijie on 11/27/17.
 */
@Entity
@Table(name = "job_group")
public class JobGroup {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;

    @ManyToOne
    @JoinColumn(name = "work_flow_id")
    private WorkFlow workFlow;

    @ManyToOne
    @JoinColumn(name = "pre_job_group_id")
    private JobGroup preJobGroup;
    private String status;

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

    public WorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(WorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public JobGroup getPreJobGroup() {
        return preJobGroup;
    }

    public void setPreJobGroup(JobGroup preJobGroup) {
        this.preJobGroup = preJobGroup;
    }
}
