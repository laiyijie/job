package me.laiyijie.job.admin.dao.entity;

import javax.persistence.*;

/**
 * Created by laiyijie on 11/27/17.
 */
@Entity
@Table(name = "job_group")
public class TbJobGroup {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private String status;

    @ManyToOne
    @JoinColumn(name = "work_flow_id")
    private TbWorkFlow workFlow;

    @ManyToOne
    @JoinColumn(name = "pre_job_group_id")
    private TbJobGroup preJobGroup;

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

    public TbWorkFlow getWorkFlow() {
        return workFlow;
    }

    public void setWorkFlow(TbWorkFlow workFlow) {
        this.workFlow = workFlow;
    }

    public TbJobGroup getPreJobGroup() {
        return preJobGroup;
    }

    public void setPreJobGroup(TbJobGroup preJobGroup) {
        this.preJobGroup = preJobGroup;
    }
}
