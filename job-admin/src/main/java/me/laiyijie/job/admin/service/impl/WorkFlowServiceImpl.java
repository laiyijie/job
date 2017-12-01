package me.laiyijie.job.admin.service.impl;

import me.laiyijie.job.admin.dao.*;
import me.laiyijie.job.admin.dao.entity.*;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.admin.service.exception.BusinessException;
import me.laiyijie.job.admin.service.mq.JobQueueService;
import me.laiyijie.job.message.RunningStatus;
import me.laiyijie.job.message.executor.RunJobMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
@Transactional
public class WorkFlowServiceImpl implements WorkFlowService {

    @Autowired
    private TbWorkFlowRepository tbWorkFlowRepository;
    @Autowired
    private TbJobGroupRepository tbJobGroupRepository;
    @Autowired
    private TbJobRepository tbJobRepository;
    @Autowired
    private TbExecutorGroupRepository tbExecutorGroupRepository;
    @Autowired
    private TbExecutorRepository tbExecutorRepository;

    @Autowired
    private JobQueueService jobQueueService;

    @Override
    public TbWorkFlow createWorkFlow(TbWorkFlow tbWorkFlow) {
        tbWorkFlow.setId(null);
        return tbWorkFlowRepository.save(tbWorkFlow);
    }

    @Override
    public TbJobGroup createJobGroup(TbJobGroup tbJobGroup) {
        //TODO need to add the constraint
        tbJobGroup.setId(null);
        return tbJobGroupRepository.save(tbJobGroup);
    }

    @Override
    public TbJob createJob(TbJob job) {
        //TODO need to add the constraint
        job.setId(null);
        return tbJobRepository.save(job);
    }

    @Override
    public TbWorkFlow modifyWorkFlow(TbWorkFlow tbWorkFlow) {
        TbWorkFlow workFlow = tbWorkFlowRepository.findOne(tbWorkFlow.getId());
        if (workFlow == null) {
            throw new BusinessException("this work flow not exist");
        }
        if (RunningStatus.RUNNING.equals(workFlow.getStatus())) {
            throw new BusinessException("this job is running , cannot modify!");
        }
        return tbWorkFlowRepository.save(tbWorkFlow);
    }

    @Override
    public TbJobGroup modifyJobGroup(TbJobGroup tbJobGroup) {
        TbJobGroup group = tbJobGroupRepository.findOne(tbJobGroup.getId());
        if (group == null) {
            throw new BusinessException("this job group not exist");
        }
        if (RunningStatus.RUNNING.equals(group.getStatus()))
            throw new BusinessException("job is running, cannot modify");
        return tbJobGroupRepository.save(tbJobGroup);
    }

    @Override
    public TbJob modifyJob(TbJob job) {
        TbJob tbJob = tbJobRepository.findOne(job.getId());
        if (tbJob == null) {
            throw new BusinessException("this job not exist");
        }
        if (RunningStatus.RUNNING.equals(tbJob.getStatus())) {
            throw new BusinessException("job is running , cannot modify ");
        }
        return tbJobRepository.save(job);
    }

    @Override
    public void deleteWorkFlow(Integer id) {
        TbWorkFlow tbWorkFlow = tbWorkFlowRepository.findOne(id);
        if (tbWorkFlow == null)
            return;
        if (RunningStatus.RUNNING.equals(tbWorkFlow.getStatus())) {
            throw new BusinessException("job is running , cannot delete ");
        }
        tbWorkFlowRepository.delete(id);
    }

    @Override
    public void deleteJobGroup(Integer id) {
        TbJobGroup tbJobGroup = tbJobGroupRepository.findOne(id);
        if (tbJobGroup == null)
            return;
        if (RunningStatus.RUNNING.equals(tbJobGroup.getStatus())) {
            throw new BusinessException("job is running cannot delete!");
        }
        tbJobGroupRepository.delete(id);
    }

    @Override
    public void deleteJob(Integer id) {
        TbJob tbJob = tbJobRepository.findOne(id);
        if (tbJob == null) {
            return;
        }
        if (RunningStatus.RUNNING.equals(tbJob.getStatus())) {
            throw new BusinessException("job is running , cannot delete!");
        }
        tbJobRepository.delete(id);
    }

    @Override
    public void runWorkFlow(Integer id) {
        TbWorkFlow workFlow = tbWorkFlowRepository.findOne(id);
        if (workFlow == null)
            throw new BusinessException("workflow not exist!");
        if (RunningStatus.RUNNING.equals(workFlow.getStatus()))
            throw new BusinessException("workflow is running!");
        workFlow.setStatus(RunningStatus.RUNNING);
        tbJobGroupRepository.findAllByWorkFlow_Id(id).forEach((group) -> {
            group.setStatus(RunningStatus.INIT);
            tbJobGroupRepository.save(group);
        });
        tbJobRepository.findALlByJobGroup_WorkFlow_id(id).forEach((job) -> {
            job.setStatus(RunningStatus.INIT);
            tbJobRepository.save(job);
        });
        tbWorkFlowRepository.save(workFlow);
    }

    @Override
    public void stopWorkFlow(Integer id) {
        //TODO

    }

    @Override
    public void resumeWorkFlow(Integer id) {
        TbWorkFlow workFlow = tbWorkFlowRepository.findOne(id);
        if (workFlow == null)
            throw new BusinessException("workflow not exist!");
        if (RunningStatus.RUNNING.equals(workFlow.getStatus()))
            throw new BusinessException("workflow is running!");
        workFlow.setStatus(RunningStatus.RUNNING);
        tbWorkFlowRepository.save(workFlow);
    }

    @Override
    public void runJob(Integer jobId) {
        TbJob job = tbJobRepository.findOne(jobId);
        if (job == null)
            return;
        if (RunningStatus.RUNNING.equals(job.getStatus()))
            return;

        jobQueueService.sendRunJobToExecutor(
                getSuitableExecutorFromExecutorGroup(job.getExecutorGroup()).getName(),
                new RunJobMsg(job.getId(), job.getScript()));

        job.setStatus(RunningStatus.RUNNING);
        tbJobRepository.save(job);
    }

    private TbExecutor getSuitableExecutorFromExecutorGroup(TbExecutorGroup tbExecutorGroup) {
        List<TbExecutor> tbExecutors = tbExecutorRepository.findAllByExecutorGroup_Name(tbExecutorGroup.getName());
        if (tbExecutors.isEmpty())
            throw new BusinessException("no available machine!");
        return tbExecutors.get(0);
    }
}
