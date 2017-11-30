package me.laiyijie.job.message.executor;

import java.io.Serializable;

/**
 * Created by laiyijie on 11/30/17.
 */
public class StopJobMsg implements Serializable {
    private Integer jobId;

    public StopJobMsg(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
}
