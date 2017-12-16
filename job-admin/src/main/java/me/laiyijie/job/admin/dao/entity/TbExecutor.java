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
    private String name;
    private String ipAddress;
    private String onlineStatus;
    private Long lastHeartBeatTime;
    private Double cpuLoad;
    private Long freeMemory;
    private Long totalMemory;

    @ManyToOne
    @JoinColumn(name = "executor_group_name")
    private TbExecutorGroup executorGroup;

    public Long getLastHeartBeatTime() {
        return lastHeartBeatTime;
    }

    public void setLastHeartBeatTime(Long lastHeartBeatTime) {
        this.lastHeartBeatTime = lastHeartBeatTime;
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

    public Double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(Double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public Long getFreeMemory() {
        return freeMemory;
    }

    public void setFreeMemory(Long freeMemory) {
        this.freeMemory = freeMemory;
    }

    public Long getTotalMemory() {
        return totalMemory;
    }

    public void setTotalMemory(Long totalMemory) {
        this.totalMemory = totalMemory;
    }
}
