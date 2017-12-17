package me.laiyijie.job.admin.service.impl;

import me.laiyijie.job.admin.dao.*;
import me.laiyijie.job.admin.dao.entity.*;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.admin.service.exception.BusinessException;
import me.laiyijie.job.admin.service.mq.JobQueueService;
import me.laiyijie.job.message.RunningStatus;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.executor.StopJobMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Comparator;
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

    private Logger log = LoggerFactory.getLogger(WorkFlowServiceImpl.class);

    @Override
    public TbWorkFlow createWorkFlow(TbWorkFlow tbWorkFlow) {
        tbWorkFlow.setId(null);
        tbWorkFlow.setStatus(RunningStatus.INIT);
        tbWorkFlow.setLastRunTime(System.currentTimeMillis());
        return tbWorkFlowRepository.save(tbWorkFlow);
    }

    @Override
    public TbJobGroup createJobGroup(TbJobGroup tbJobGroup) {
        //TODO need to add the constraint
        tbJobGroup.setId(null);
        tbJobGroup.setStatus(RunningStatus.INIT);
        tbJobGroup.setWorkFlow(tbJobGroup.getWorkFlow());

        return tbJobGroupRepository.save(tbJobGroup);
    }

    @Override
    public TbJob createJob(TbJob job) {
        //TODO need to add the constraint
        job.setId(null);
        job.setStatus(RunningStatus.INIT);

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

        workFlow.setDescription(tbWorkFlow.getDescription());
        workFlow.setRunInterval(tbWorkFlow.getRunInterval());
        workFlow.setName(tbWorkFlow.getName());
        workFlow.setScheduled(tbWorkFlow.getScheduled());

        return tbWorkFlowRepository.save(workFlow);
    }

    @Override
    public TbJobGroup modifyJobGroup(TbJobGroup tbJobGroup) {
        TbJobGroup group = tbJobGroupRepository.findOne(tbJobGroup.getId());
        if (group == null) {
            throw new BusinessException("this job group not exist");
        }
        if (RunningStatus.RUNNING.equals(group.getStatus()))
            throw new BusinessException("job is running, cannot modify");

        group.setName(tbJobGroup.getName());
        group.setStep(tbJobGroup.getStep());
        group.setDescription(tbJobGroup.getDescription());

        return tbJobGroupRepository.save(group);
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

        tbJob.setDescription(job.getDescription());
        tbJob.setName(job.getName());
        tbJob.setExecutorGroup(job.getExecutorGroup());
        tbJob.setScript(job.getScript());

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
        tbJobGroupRepository.findAllByWorkFlow_Id(id)
                .forEach((group) -> {
                    group.setStatus(RunningStatus.INIT);
                    tbJobGroupRepository.save(group);
                });
        tbJobRepository.findALlByJobGroup_WorkFlow_id(id)
                .forEach((job) -> {
                    job.setStatus(RunningStatus.INIT);
                    tbJobRepository.save(job);
                });
        workFlow.setLastRunTime(System.currentTimeMillis());
        tbWorkFlowRepository.save(workFlow);
    }

    @Override
    public void stopWorkFlow(Integer id) {
        TbWorkFlow workFlow = tbWorkFlowRepository.findOne(id);
        if (workFlow == null)
            throw new BusinessException("workflow not exist!");
        if (!RunningStatus.RUNNING.equals(workFlow.getStatus()))
            throw new BusinessException("cannot stop not running workflow");
        workFlow.setStatus(RunningStatus.STOPPING);
        tbWorkFlowRepository.save(workFlow);
        List<TbJob> runningJobs = tbJobRepository.findAllByJobGroup_WorkFlow_IdAndStatus(id, RunningStatus.RUNNING);
        for (TbJob job : runningJobs) {
            stopJob(job.getId());
        }
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
        TbExecutor tbExecutor = getSuitableExecutorFromExecutorGroup(
                job.getExecutorGroup());
        if (tbExecutor == null) {
            job.setStatus(RunningStatus.FAILED);
            log.error("job_id: " + jobId + "  executor_group_name:" + job.getExecutorGroup()
                    .getName() + " error msg: no executors online ");
            tbJobRepository.save(job);
            return;
        }
        jobQueueService.sendRunJobToExecutor(tbExecutor.getName(),
                new RunJobMsg(job.getJobGroup()
                        .getWorkFlow()
                        .getId(), job.getJobGroup()
                        .getId(), job.getId(), job.getScript()));

        job.setStatus(RunningStatus.RUNNING);
        job.setLastRunningBeatTime(System.currentTimeMillis());
        tbJobRepository.save(job);
    }

    @Override
    public void stopJob(Integer jobId) {
        TbJob job = tbJobRepository.findOne(jobId);
        if (job == null)
            return;
        if (!RunningStatus.RUNNING.equals(job.getStatus()))
            return;
        jobQueueService.sendStopJobToExecutor(job.getCurrentExecutor()
                .getName(), new StopJobMsg(job.getId()));
    }
        
    private TbExecutor getSuitableExecutorFromExecutorGroup(TbExecutorGroup tbExecutorGroup) {
        List<TbExecutor> tbExecutors = tbExecutorRepository.findAllByExecutorGroup_Name(tbExecutorGroup.getName());
        if (tbExecutors.isEmpty())
            return null;
        tbExecutors.sort(Comparator.comparingLong(TbExecutor::getFreeMemory));
        return tbExecutors.get(tbExecutors.size() - 1);
    }
}
