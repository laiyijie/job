package me.laiyijie.job.admin.web.controller;

import io.swagger.annotations.ApiParam;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.service.ExecutorService;
import me.laiyijie.job.admin.show.ExecutorShow;
import me.laiyijie.job.admin.show.converter.ExecutorConverter;
import me.laiyijie.job.swagger.api.ExecutorApi;
import me.laiyijie.job.swagger.api.RulesApi;
import me.laiyijie.job.swagger.model.ExecutorGroup;
import me.laiyijie.job.swagger.model.Rule;
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
public class RuleController implements RulesApi {

    @Override
    public ResponseEntity<List<Rule>> rulesGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> rulesPost(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> rulesRuleIdDelete(@ApiParam(value = "", required = true) @PathVariable("ruleId") Integer ruleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<Void> rulesRuleIdPut(@ApiParam(value = "", required = true) @PathVariable("ruleId") Integer ruleId, @ApiParam(value = "", required = true) @Valid @RequestBody Rule rule, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

}
