package me.laiyijie.job.message.command;

import java.io.Serializable;

/**
 * Created by laiyijie on 11/29/17.
 */
public class HeartBeatMsg implements Serializable {

    private String executorName;
    private String groupName;

    public HeartBeatMsg(String groupName, String executorName){
        this.groupName = groupName;
        this.executorName = executorName;
    }
    public String getExecutorName() {
        return executorName;
    }

    public void setExecutorName(String executorName) {
        this.executorName = executorName;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "HeartBeatMsg{" +
                "executorName='" + executorName + '\'' +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
