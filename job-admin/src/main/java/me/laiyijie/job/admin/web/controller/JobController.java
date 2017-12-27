package me.laiyijie.job.admin.web.controller;

import io.swagger.annotations.ApiParam;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.admin.show.WorkFlowShow;
import me.laiyijie.job.admin.show.converter.WorkFlowConverter;
import me.laiyijie.job.swagger.api.JobApi;
import me.laiyijie.job.swagger.api.JobsApi;
import me.laiyijie.job.swagger.api.WorkflowsApi;
import me.laiyijie.job.swagger.model.Job;
import me.laiyijie.job.swagger.model.JobErrorLog;
import me.laiyijie.job.swagger.model.JobGroup;
import me.laiyijie.job.swagger.model.WorkFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by laiyijie on 12/2/17.
 */
@RestController
public class JobController implements JobApi, JobsApi, WorkflowsApi {

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private WorkFlowShow workFlowShow;
    @Autowired
    private WorkFlowConverter workFlowConverter;

    @Override
    public ResponseEntity<List<JobErrorLog>> jobErrorLogsGet(@NotNull @ApiParam(value = "", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize, @NotNull @ApiParam(value = "", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Pageable pageable = new PageRequest(pageNum, pageSize);
        return ResponseEntity.ok(workFlowShow.getAllJobErrorLog(pageable));
    }

    @Override
    public ResponseEntity<Void> jobGroupsGroupIdDelete(@ApiParam(value = "", required = true) @PathVariable("groupId") Integer groupId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.deleteJobGroup(groupId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<JobGroup> jobGroupsGroupIdGet(@ApiParam(value = "", required = true) @PathVariable("groupId") Integer groupId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        JobGroup jobGroup = workFlowShow.getJobGroup(groupId);
        if (jobGroup == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(jobGroup);
    }

    @Override
    public ResponseEntity<List<Job>> jobGroupsGroupIdJobsGet(@ApiParam(value = "", required = true) @PathVariable("groupId") Integer groupId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(workFlowShow.getJobsInGroup(groupId));
    }

    @Override
    public ResponseEntity<Void> jobGroupsGroupIdPost(@ApiParam(value = "", required = true) @PathVariable("groupId") Integer groupId, @ApiParam(value = "", required = true) @Valid @RequestBody JobGroup jobGroup, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbJobGroup tbJobGroup = workFlowConverter.convertToTbJobGroup(jobGroup);
        tbJobGroup.setId(groupId);
        workFlowService.modifyJobGroup(tbJobGroup);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> jobGroupsPost(@ApiParam(value = "", required = true) @Valid @RequestBody JobGroup jobGroup, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.createJobGroup(workFlowConverter.convertToTbJobGroup(jobGroup));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> jobsJobIdDelete(@ApiParam(value = "", required = true) @PathVariable("jobId") Integer jobId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.deleteJob(jobId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<JobErrorLog>> jobsJobIdErrorLogGet(@ApiParam(value = "", required = true) @PathVariable("jobId") Integer jobId, @NotNull @ApiParam(value = "", required = true) @RequestParam(value = "pageSize", required = true) Integer pageSize, @NotNull @ApiParam(value = "", required = true) @RequestParam(value = "pageNum", required = true) Integer pageNum, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(workFlowShow.getJobErrorLogByJobId(jobId, new PageRequest(pageNum, pageSize)));
    }

    @Override
    public ResponseEntity<Job> jobsJobIdGet(@ApiParam(value = "", required = true) @PathVariable("jobId") Integer jobId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Job job = workFlowShow.getJob(jobId);
        if (job == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(job);

    }

    @Override
    public ResponseEntity<Void> jobsJobIdPost(@ApiParam(value = "", required = true) @PathVariable("jobId") Integer jobId, @ApiParam(value = "", required = true) @Valid @RequestBody Job job, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbJob tbJob = workFlowConverter.convertToTbJob(job);
        tbJob.setId(jobId);
        workFlowService.modifyJob(tbJob);
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> jobsJobIdRunPost(@ApiParam(value = "", required = true) @PathVariable("jobId") Integer jobId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.runJob(jobId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> jobsJobIdStopPost(@ApiParam(value = "", required = true) @PathVariable("jobId") Integer jobId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.stopJob(jobId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> jobsPost(@ApiParam(value = "", required = true) @Valid @RequestBody Job job, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbJob tbJob = workFlowConverter.convertToTbJob(job);
        workFlowService.createJob(tbJob);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<WorkFlow>> workflowsGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(workFlowShow.getWorkFlows());
    }

    @Override
    public ResponseEntity<Integer> workflowsPost(@ApiParam(value = "", required = true) @Valid @RequestBody WorkFlow workFlow, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbWorkFlow tbWorkFlow = workFlowConverter.convertToTbWorkFlow(workFlow);
        tbWorkFlow = workFlowService.createWorkFlow(tbWorkFlow);
        return ResponseEntity.ok(tbWorkFlow.getId());
    }

    @Override
    public ResponseEntity<Void> workflowsWorkFlowIdDelete(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.deleteWorkFlow(workFlowId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<WorkFlow> workflowsWorkFlowIdGet(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        WorkFlow workFlow = workFlowShow.getWorkFlow(workFlowId);
        if (workFlow == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(workFlow);
    }

    @Override
    public ResponseEntity<List<JobGroup>> workflowsWorkFlowIdJobGroupsGet(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(workFlowShow.getJobGroupsInWorkFlow(workFlowId));
    }

    @Override
    public ResponseEntity<Void> workflowsWorkFlowIdPost(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, @ApiParam(value = "", required = true) @Valid @RequestBody WorkFlow workFlow, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbWorkFlow tbWorkFlow = workFlowConverter.convertToTbWorkFlow(workFlow);
        tbWorkFlow.setId(workFlowId);
        workFlowService.modifyWorkFlow(tbWorkFlow);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> workflowsWorkFlowIdResumePost(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.resumeWorkFlow(workFlowId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> workflowsWorkFlowIdRunPost(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.runWorkFlow(workFlowId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> workflowsWorkFlowIdStopPost(@ApiParam(value = "", required = true) @PathVariable("workFlowId") Integer workFlowId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        workFlowService.stopWorkFlow(workFlowId);
        return ResponseEntity.ok().build();
    }


}
