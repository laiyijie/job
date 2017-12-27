package me.laiyijie.job.admin.show;

import me.laiyijie.job.admin.dao.TbExecutorGroupRepository;
import me.laiyijie.job.admin.dao.TbRuleRepository;
import me.laiyijie.job.admin.show.converter.ExecutorConverter;
import me.laiyijie.job.admin.show.converter.RuleConverter;
import me.laiyijie.job.swagger.model.ExecutorGroup;
import me.laiyijie.job.swagger.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class RuleShow {
    @Autowired
    private TbRuleRepository tbRuleRepository;
    @Autowired
    private RuleConverter ruleConverter;

    public List<Rule> getAllRules() {
        return tbRuleRepository.findAll().stream().map((
                tbRule -> ruleConverter.convertToRule(tbRule)))
                .collect(Collectors.toList());
    }


}
