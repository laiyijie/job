package me.laiyijie.job.admin.service.schedule;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.*;
import me.laiyijie.job.admin.dao.entity.TbExecutor;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.admin.service.exception.BusinessException;
import me.laiyijie.job.message.RunningStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
@EnableScheduling
public class WorkFlowSchedule {

    private Logger log = LoggerFactory.getLogger(WorkFlowSchedule.class);
    @Autowired
    private TbWorkFlowRepository tbWorkFlowRepository;
    @Autowired
    private TbJobGroupRepository tbJobGroupRepository;
    @Autowired
    private TbJobRepository tbJobRepository;
    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private TbExecutorGroupRepository tbExecutorGroupRepository;
    @Autowired
    private TbExecutorRepository tbExecutorRepository;

    private final Integer OFFLINE_TIME = 60;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRunningWorkFlows() {
        log.debug(" in  schedule running work flow!");
        log.debug(" running workflow info: " + tbWorkFlowRepository.findAll());
        List<TbWorkFlow> workFlows = tbWorkFlowRepository.findAllByStatus(RunningStatus.RUNNING);
        for (TbWorkFlow workFlow : workFlows) {
            try {

                log.debug("schedule running work: " + workFlow.getId());
                TbJobGroup tbJobGroup = getCurrentWorkGroup(workFlow.getId());
                if (tbJobGroup == null) {
                    workFlow.setStatus(RunningStatus.FINISHED);
                    tbWorkFlowRepository.save(workFlow);
                    continue;
                }
                List<TbJob> tbJobs = tbJobRepository.findAllByJobGroup_Id(tbJobGroup.getId());

                if (RunningStatus.INIT.equals(tbJobGroup.getStatus())) {
                    tbJobs.forEach((job) -> {
                        workFlowService.runJob(job.getId());
                    });
                    tbJobGroup.setStatus(RunningStatus.RUNNING);
                    tbJobGroupRepository.save(tbJobGroup);
                    continue;
                }

                if (RunningStatus.STOPPED.equals(tbJobGroup.getStatus()) ||
                        RunningStatus.FAILED.equals(tbJobGroup.getStatus())) {
                    tbJobs.forEach((job) -> {
                        if (!RunningStatus.FINISHED.equals(job.getStatus())) {
                            workFlowService.runJob(job.getId());
                        }
                    });
                    tbJobGroup.setStatus(RunningStatus.RUNNING);
                    tbJobGroupRepository.save(tbJobGroup);
                    continue;
                }
                // check failed job to test if retry
                boolean isRetry = false;
                for (TbJob job : tbJobs) {
                    if (RunningStatus.FAILED.equals(job.getStatus())) {
                        // retry the rule
                        if (job.getRuleRetryFlag() && job.getRuleRetryTimes() < job.getRuleMaxRetryTimes()) {
                            job.setRuleRetryTimes(job.getRuleRetryTimes() + 1);
                            job.setRuleRetryFlag(false);
                            tbJobRepository.save(job);
                            workFlowService.internalRunJob(job.getId());
                            isRetry = true;
                        }
                    }
                }
                // skip update the workflow and group status , when retry
                if (isRetry)
                    continue;

                // update the workflow and group status, after schedule
                Boolean isHaveFailed = false;
                Boolean isAllFinish = true;
                for (TbJob job : tbJobs) {
                    if (RunningStatus.FAILED.equals(job.getStatus())) {
                        isHaveFailed = true;
                        continue;
                    }
                    if (RunningStatus.FINISHED.equals(job.getStatus()))
                        continue;
                    isAllFinish = false;
                }
                if (isAllFinish) {
                    if (isHaveFailed) {
                        tbJobGroup.setStatus(RunningStatus.FAILED);
                        workFlow.setStatus(RunningStatus.FAILED);
                    } else {
                        tbJobGroup.setStatus(RunningStatus.FINISHED);
                    }
                    tbJobGroupRepository.save(tbJobGroup);
                    tbWorkFlowRepository.save(workFlow);
                }
            } catch (BusinessException ex) {
                workFlow.setStatus(RunningStatus.FAILED);
                tbWorkFlowRepository.save(workFlow);
                log.error(" errorMsg:" + ex.getMsg());
            }
        }

    }

    @Scheduled(fixedDelay = 1000)
    public void retryNotRunningWorkFlowJob() {
        List<TbJob> tbJobs = tbJobRepository.findAllByStatusAndJobGroup_WorkFlow_StatusNot(RunningStatus.FAILED, RunningStatus.RUNNING);
        for (TbJob job : tbJobs) {
            if (RunningStatus.FAILED.equals(job.getStatus())) {
                // retry the rule
                if (job.getRuleRetryFlag() && job.getRuleRetryTimes() < job.getRuleMaxRetryTimes()) {
                    job.setRuleRetryTimes(job.getRuleRetryTimes() + 1);
                    job.setRuleRetryFlag(false);
                    tbJobRepository.save(job);
                    workFlowService.internalRunJob(job.getId());
                }
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleCircleWorkFlows() {
        List<TbWorkFlow> workFlows = tbWorkFlowRepository.findAllByScheduledIsTrue();
        log.debug("schedule circle" + JSON.toJSONString(workFlows));
        for (TbWorkFlow workFlow : workFlows) {
            if (workFlow.getRunInterval() <= 0) {
                continue;
            }
            if (workFlow.getLastRunTime() + workFlow.getRunInterval() * 1000 > System.currentTimeMillis()) {
                continue;
            }
            if (RunningStatus.RUNNING.equals(workFlow.getStatus())) {
                continue;
            }
            try {
                workFlowService.runWorkFlow(workFlow.getId());
            } catch (BusinessException ex) {
                log.error("workflow_id: " + workFlow.getId() + " errorMsg:" + ex.getMsg());
            }
        }
    }

    @Scheduled(fixedDelay = 3000)
    public void refreshStoppingWorkFlows() {
        log.debug(" in  schedule stopping work flow!");
        log.debug(" stopping workflow info: " + tbWorkFlowRepository.findAll());
        List<TbWorkFlow> workFlows = tbWorkFlowRepository.findAllByStatus(RunningStatus.STOPPING);
        for (TbWorkFlow workFlow : workFlows) {
            TbJobGroup tbJobGroup = getCurrentWorkGroup(workFlow.getId());
            if (tbJobGroup == null) {
                workFlow.setStatus(RunningStatus.STOPPED);
                tbWorkFlowRepository.save(workFlow);
                continue;
            }
            Long jobs = tbJobRepository.countByJobGroup_WorkFlow_IdAndStatus(workFlow.getId(), RunningStatus.STOPPING);
            jobs += tbJobRepository.countByJobGroup_WorkFlow_IdAndStatus(workFlow.getId(), RunningStatus.STOPPING);
            if (jobs != 0)
                continue;
            tbJobGroup.setStatus(RunningStatus.STOPPED);
            tbJobGroupRepository.save(tbJobGroup);
            workFlow.setStatus(RunningStatus.STOPPED);
            tbWorkFlowRepository.save(workFlow);
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void checkOfflineExecutor() {
        List<TbExecutor> executors = tbExecutorRepository.findAllByLastHeartBeatTimeLessThan(System.currentTimeMillis() - OFFLINE_TIME * 1000);
        log.debug("executors:" + JSON.toJSONString(executors));
        for (TbExecutor executor : executors) {
            executor.setOnlineStatus(TbExecutor.OFFLINE);
            tbExecutorRepository.save(executor);
        }

    }

    @Scheduled(fixedDelay = 5000)
    public void checkOfflineJob() {
        List<TbJob> jobs = tbJobRepository.findAllByStatusAndLastRunningBeatTimeLessThan(RunningStatus.RUNNING,
                System.currentTimeMillis() - OFFLINE_TIME * 1000);
        log.debug("check off line job: " + JSON.toJSONString(jobs));
        for (TbJob job : jobs) {
            job.setStatus(RunningStatus.FAILED);
            tbJobRepository.save(job);
        }
    }

    private TbJobGroup getCurrentWorkGroup(Integer workFlowId) {
        List<TbJobGroup> jobGroups = tbJobGroupRepository.findAllByWorkFlow_IdOrderByStep(workFlowId);
        if (jobGroups.isEmpty()) return null;
        for (TbJobGroup curr : jobGroups) {
            if (RunningStatus.RUNNING.equals(curr.getStatus()))
                return curr;
            if (RunningStatus.STOPPED.equals(curr.getStatus()) || RunningStatus.FAILED.equals(curr.getStatus()))
                return curr;
            if (RunningStatus.INIT.equals(curr.getStatus()))
                return curr;
        }
        return null;
    }
}
