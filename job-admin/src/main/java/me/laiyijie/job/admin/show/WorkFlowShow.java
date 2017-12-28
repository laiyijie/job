package me.laiyijie.job.admin.show;

import me.laiyijie.job.admin.dao.TbJobErrorLogRepository;
import me.laiyijie.job.admin.dao.TbJobGroupRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.TbWorkFlowRepository;
import me.laiyijie.job.admin.dao.entity.TbJobErrorLog;
import me.laiyijie.job.admin.show.converter.WorkFlowConverter;
import me.laiyijie.job.swagger.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class WorkFlowShow {
    @Autowired
    private WorkFlowConverter workFlowConverter;
    @Autowired
    private TbJobGroupRepository tbJobGroupRepository;
    @Autowired
    private TbJobRepository tbJobRepository;
    @Autowired
    private TbWorkFlowRepository tbWorkFlowRepository;
    @Autowired
    private TbJobErrorLogRepository tbJobErrorLogRepository;

    public List<Job> getJobsInGroup(Integer groupId) {
        return tbJobRepository.findAllByJobGroup_Id(groupId).stream()
                .map((job) -> workFlowConverter.convertToJob(job)).collect(Collectors.toList());
    }

    public Job getJob(Integer jobId) {
        return workFlowConverter.convertToJob(tbJobRepository.findOne(jobId));
    }

    public List<JobGroup> getJobGroupsInWorkFlow(Integer workFlowId) {
        return tbJobGroupRepository.findAllByWorkFlow_IdOrderByStep(workFlowId).stream()
                .map((jobGroup) -> workFlowConverter.convertToJobGroup(jobGroup))
                .collect(Collectors.toList());
    }

    public JobGroup getJobGroup(Integer jobGroupId) {
        return workFlowConverter.convertToJobGroup(tbJobGroupRepository.findOne(jobGroupId));
    }

    public List<WorkFlow> getWorkFlows() {
        return tbWorkFlowRepository.findAll().stream()
                .map((workflow) -> workFlowConverter.convertToWorkFlow(workflow))
                .collect(Collectors.toList());
    }

    public WorkFlow getWorkFlow(Integer workFlowId) {
        return workFlowConverter.convertToWorkFlow(tbWorkFlowRepository.findOne(workFlowId));
    }

//    public List<JobErrorLog> getAllJobErrorLog(Pageable pageable) {
//        return tbJobErrorLogRepository.findAllByOrderByLogTimeDesc(pageable)
//                .stream()
//                .map((log) -> workFlowConverter.convertToJobErrorLog(log))
//                .collect(Collectors.toList());
//    }

    public ErrorLogResponse getJobErrorLogByJobIdAndWorkFlowIdAndTime(Integer jobId, Integer workflowId, Long startTime, Long endTime, Pageable pageable) {
        Page<TbJobErrorLog> result;
        if (jobId == null && workflowId == null) {
            result = tbJobErrorLogRepository.findAllByLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(endTime, startTime, pageable);
        } else {
            if (jobId == null) {
                result = tbJobErrorLogRepository.findAllByWorkflowIdAndLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(workflowId, endTime, startTime, pageable);
            } else {
                if (workflowId == null) {
                    result = tbJobErrorLogRepository.findAllByJobIdAndLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(jobId, endTime, startTime, pageable);
                } else {
                    result = tbJobErrorLogRepository.findAllByJobIdAndWorkflowIdAndLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(jobId, workflowId, endTime, startTime, pageable);
                }
            }
        }
        ErrorLogResponse errorLogResponse = new ErrorLogResponse();
        errorLogResponse.setTotalRows(result.getTotalElements());
        errorLogResponse.setLogs(
                result
                        .getContent()
                        .stream()
                        .map((log) -> workFlowConverter.convertToJobErrorLog(log))
                        .collect(Collectors.toList())
        );
        return errorLogResponse;
    }
}
