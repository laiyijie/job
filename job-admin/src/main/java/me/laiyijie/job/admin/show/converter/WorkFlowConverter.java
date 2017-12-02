package me.laiyijie.job.admin.show.converter;

import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.swagger.model.Job;
import me.laiyijie.job.swagger.model.JobGroup;
import me.laiyijie.job.swagger.model.WorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class WorkFlowConverter {
    @Autowired
    private ExecutorConverter executorConverter;

    public WorkFlow convertToWorkFlow(TbWorkFlow tbWorkFlow) {
        if (tbWorkFlow == null) return null;
        WorkFlow workFlow = new WorkFlow();
        workFlow.setDescription(tbWorkFlow.getDescription());
        workFlow.setStatus(tbWorkFlow.getStatus());
        workFlow.setName(tbWorkFlow.getName());
        workFlow.setId(tbWorkFlow.getId());
        workFlow.setIsCircleScheduled(tbWorkFlow.getScheduled());
        workFlow.setRunInterval(tbWorkFlow.getRunInterval());
        return workFlow;
    }

    public JobGroup convertToJobGroup(TbJobGroup tbJobGroup) {
        if (tbJobGroup == null) return null;
        JobGroup jobGroup = new JobGroup();
        jobGroup.setStatus(tbJobGroup.getStatus());
        jobGroup.setId(tbJobGroup.getId());
        jobGroup.setName(tbJobGroup.getName());
        jobGroup.setDescription(tbJobGroup.getDescription());
        jobGroup.setStep(tbJobGroup.getStep());
        return jobGroup;
    }

    public Job convertToJob(TbJob tbJob) {
        if (tbJob == null) return null;
        Job job = new Job();
        job.setDescription(tbJob.getDescription());
        job.setStatus(tbJob.getStatus());
        job.setName(tbJob.getName());
        job.setId(tbJob.getId());
        job.setScript(tbJob.getScript());
        job.setExecutorGroup(executorConverter.convertToExecutorGroupWithoutExecutors(tbJob.getExecutorGroup()));
        job.setCurrentExecutor(executorConverter.convertToExecutor(tbJob.getCurrentExecutor()));
        return job;
    }

}
