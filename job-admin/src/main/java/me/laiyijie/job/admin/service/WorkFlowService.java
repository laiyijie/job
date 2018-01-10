package me.laiyijie.job.admin.service;

import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/30/17.
 */
public interface WorkFlowService {
    String ALGORITHM_DEFAULT = "DEFAULT";
    String ALGORITHM_ALL_ONLINE_EXECUTOR = "ALL_ONLINE_EXECUTOR";

    TbWorkFlow createWorkFlow(TbWorkFlow tbWorkFlow);

    TbJobGroup createJobGroup(TbJobGroup tbJobGroup);

    TbJob createJob(TbJob job);

    TbWorkFlow modifyWorkFlow(TbWorkFlow tbWorkFlow);

    TbJobGroup modifyJobGroup(TbJobGroup tbJobGroup);

    TbJob modifyJob(TbJob job);

    void deleteWorkFlow(Integer id);

    void deleteJobGroup(Integer id);

    void deleteJob(Integer id);

    void runWorkFlow(Integer id);

    void stopWorkFlow(Integer id);

    void resumeWorkFlow(Integer id);

    void runJob(Integer jobId);

    void internalRunJob(Integer jobId);

    void stopJob(Integer jobId);
}
