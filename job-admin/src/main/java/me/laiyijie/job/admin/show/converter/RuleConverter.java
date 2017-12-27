package me.laiyijie.job.admin.show.converter;

import me.laiyijie.job.admin.dao.TbExecutorRepository;
import me.laiyijie.job.admin.dao.entity.TbExecutor;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.dao.entity.TbRule;
import me.laiyijie.job.swagger.model.Executor;
import me.laiyijie.job.swagger.model.ExecutorGroup;
import me.laiyijie.job.swagger.model.Rule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.stream.Collectors;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class RuleConverter {

    public Rule convertToRule(TbRule tbRule) {
        if (tbRule == null)
            return null;
        Rule rule = new Rule();
        rule.setId(tbRule.getId());
        rule.setPattern(tbRule.getPattern());
        rule.setRetryTimes(tbRule.getRetryTimes());
        rule.setScript(tbRule.getScript());
        return rule;
    }

    public TbRule convertToTbRule(Rule rule){
        if (rule == null)
            return null;
        TbRule tbRule=new TbRule();
        tbRule.setScript(rule.getScript());
        tbRule.setPattern(rule.getPattern());
        tbRule.setId(rule.getId());
        tbRule.setRetryTimes(rule.getRetryTimes());
        return tbRule;
    }

}
