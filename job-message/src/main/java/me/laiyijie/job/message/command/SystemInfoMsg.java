package me.laiyijie.job.message.command;

import java.io.Serializable;

/**
 * Created by laiyijie on 12/16/17.
 */
public class SystemInfoMsg implements Serializable {
    private String groupName;
    private String executorName;
    private Double cpuLoad;
    private Long freePhysicalMemory;
    private Long totalPhysicalMemory;

    public Double getCpuLoad() {
        return cpuLoad;
    }

    public void setCpuLoad(Double cpuLoad) {
        this.cpuLoad = cpuLoad;
    }

    public Long getFreePhysicalMemory() {
        return freePhysicalMemory;
    }

    public void setFreePhysicalMemory(Long freePhysicalMemory) {
        this.freePhysicalMemory = freePhysicalMemory;
    }

    public Long getTotalPhysicalMemory() {
        return totalPhysicalMemory;
    }

    public void setTotalPhysicalMemory(Long totalPhysicalMemory) {
        this.totalPhysicalMemory = totalPhysicalMemory;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    @Override
    public String toString() {
        return "SystemInfoMsg{" +
                "groupName='" + groupName + '\'' +
                ", executorName='" + executorName + '\'' +
                ", cpuLoad=" + cpuLoad +
                ", freePhysicalMemory=" + freePhysicalMemory +
                ", totalPhysicalMemory=" + totalPhysicalMemory +
                '}';
    }
}
