package me.laiyijie.job.admin.dao.entity;

import me.laiyijie.job.admin.dao.state.RunningStatus;

import javax.persistence.*;

/**
 * Created by laiyijie on 11/27/17.
 */

@Entity
@Table(name = "work_flow")
public class TbWorkFlow {
    @Id
    @GeneratedValue
    private Integer id;

    private String name;
    private String description;
    private Integer runInterval;
    private String status;

    public TbWorkFlow() {
    }

    public TbWorkFlow(String name, String description) {
        this.name = name;
        this.description = description;
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

    public Integer getRunInterval() {
        return runInterval;
    }

    public void setRunInterval(Integer runInterval) {
        this.runInterval = runInterval;
    }
}
