package me.laiyijie.job.admin.service.schedule;

import me.laiyijie.job.admin.dao.TbJobGroupRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.TbWorkFlowRepository;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.admin.service.mq.JobQueueService;
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

    @Scheduled(fixedDelay = 1000)
    public void scheduleRunningWorkFlows() {
        log.debug(" in  schedule running work flow!" );
        log.debug(" workflow info: " + tbWorkFlowRepository.findAll());
        List<TbWorkFlow> workFlows = tbWorkFlowRepository.findAllByStatus(RunningStatus.RUNNING);
        for (TbWorkFlow workFlow : workFlows) {
            log.debug("schedule running work: " + workFlow.getId() );
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
                    RunningStatus.FAILED.equals(tbJobGroup.getStatus())){
                tbJobs.forEach((job)->{
                    if (!RunningStatus.FINISHED.equals(job.getStatus())){
                        workFlowService.runJob(job.getId());
                    }
                });
                tbJobGroup.setStatus(RunningStatus.RUNNING);
                tbJobGroupRepository.save(tbJobGroup);
                continue;
            }

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
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleCircleWorkFlows() {

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
