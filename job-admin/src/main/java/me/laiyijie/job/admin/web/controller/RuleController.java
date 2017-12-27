package me.laiyijie.job.admin.web.controller;

import io.swagger.annotations.ApiParam;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.dao.entity.TbRule;
import me.laiyijie.job.admin.service.ExecutorService;
import me.laiyijie.job.admin.service.RuleService;
import me.laiyijie.job.admin.show.ExecutorShow;
import me.laiyijie.job.admin.show.RuleShow;
import me.laiyijie.job.admin.show.converter.ExecutorConverter;
import me.laiyijie.job.admin.show.converter.RuleConverter;
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
    @Autowired
    private RuleShow ruleShow;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private RuleConverter ruleConverter;

    @Override
    public ResponseEntity<List<Rule>> rulesGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return ResponseEntity.ok(ruleShow.getAllRules());
    }

    @Override
    public ResponseEntity<Void> rulesPost(@ApiParam(value = "", required = true) @Valid @RequestBody Rule rule, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TbRule tbRule = ruleService.createRule(ruleConverter.convertToTbRule(rule));
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> rulesRuleIdDelete(@ApiParam(value = "", required = true) @PathVariable("ruleId") Integer ruleId, HttpServletRequest request, HttpServletResponse response) throws Exception {
        ruleService.deleteRule(ruleId);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> rulesRuleIdPut(@ApiParam(value = "", required = true) @PathVariable("ruleId") Integer ruleId, @ApiParam(value = "", required = true) @Valid @RequestBody Rule rule, HttpServletRequest request, HttpServletResponse response) throws Exception {
        rule.setId(ruleId);
        ruleService.modifyRule(ruleConverter.convertToTbRule(rule));
        return ResponseEntity.ok().build();
    }

}
