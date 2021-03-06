package me.laiyijie.job.admin.web.controller;

import io.swagger.annotations.ApiParam;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.service.ExecutorService;
import me.laiyijie.job.admin.show.ExecutorShow;
import me.laiyijie.job.admin.show.converter.ExecutorConverter;
import me.laiyijie.job.swagger.api.ExecutorApi;
import me.laiyijie.job.swagger.model.ExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

/**
 * Created by laiyijie on 12/2/17.
 */
@RestController
public class ExecutorController implements ExecutorApi {
    @Autowired
    private ExecutorShow executorShow;
    @Autowired
    private ExecutorService executorService;
    @Autowired
    private ExecutorConverter executorConverter;

    @Override
    public ResponseEntity<List<ExecutorGroup>> executorGroupsGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(executorShow.getAllExecutorGroup());
    }

    @Override
    public ResponseEntity<Void> executorGroupsGroupNameDelete(@ApiParam(value = "", required = true) @PathVariable("groupName") String groupName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        executorService.deleteExecutorGroup(groupName);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<ExecutorGroup> executorGroupsGroupNameGet(@ApiParam(value = "", required = true) @PathVariable("groupName") String groupName, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ExecutorGroup executorGroup = executorShow.getExecutorGroup(groupName);
        if (executorGroup == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(executorGroup);
    }

    @Override
    public ResponseEntity<Void> executorGroupsGroupNamePut(@ApiParam(value = "", required = true) @PathVariable("groupName") String groupName, @ApiParam(value = "", required = true) @Valid @RequestBody ExecutorGroup executorGroup, HttpServletRequest request, HttpServletResponse response) throws Exception {
        executorGroup.setName(groupName);
        executorService.modifyExecutorGroup(executorConverter.convertToUpdateExecutorGroup(executorGroup));
        return ResponseEntity.ok().build();
    }


    @Override
    public ResponseEntity<Void> executorGroupsPost(@ApiParam(value = "", required = true) @Valid @RequestBody ExecutorGroup executorGroup, HttpServletRequest request, HttpServletResponse response) throws Exception {

        TbExecutorGroup group = executorService.createExecutorGroup(executorConverter.convertToUpdateExecutorGroup(executorGroup));
        return ResponseEntity.ok().build();
    }


}
