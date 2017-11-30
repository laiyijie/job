package me.laiyijie.job.admin.service.schedule;

import me.laiyijie.job.admin.dao.TbJobGroupRepository;
import me.laiyijie.job.admin.dao.TbWorkFlowRepository;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.message.RunningStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
public class WorkFlowSchedule {

    @Autowired
    private TbWorkFlowRepository tbWorkFlowRepository;
    @Autowired
    private TbJobGroupRepository tbJobGroupRepository;

    @Scheduled(fixedDelay = 1000)
    public void scheduleRunningWorkFlows() {
        List<TbWorkFlow> workFlows = tbWorkFlowRepository.findAllByStatus(RunningStatus.RUNNING);
        for (TbWorkFlow workFlow : workFlows) {

        }
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduleCircleWorkFlows() {

    }

    public TbJobGroup getCurrentWorkGroup(Integer workFlowId, TbJobGroup preJobGroup) {

    }
}
