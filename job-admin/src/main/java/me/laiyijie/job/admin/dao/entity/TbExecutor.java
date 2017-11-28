package me.laiyijie.job.admin.dao.entity;

import javax.persistence.*;

/**
 * Created by laiyijie on 11/28/17.
 */

@Entity
@Table(name = "executor")
public class TbExecutor {

    public static String ONLINE = "ONLINE";
    public static String OFFLINE = "OFFLINE";

    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private String ipAddress;
    private String onlineStatus;

    @ManyToOne
    @JoinColumn(name = "executor_group_id")
    private TbExecutorGroup executorGroup;

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

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public TbExecutorGroup getExecutorGroup() {
        return executorGroup;
    }

    public void setExecutorGroup(TbExecutorGroup executorGroup) {
        this.executorGroup = executorGroup;
    }
}
