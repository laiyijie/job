package me.laiyijie.job.admin.service.impl;

import me.laiyijie.job.admin.dao.TbRuleRepository;
import me.laiyijie.job.admin.dao.entity.TbRule;
import me.laiyijie.job.admin.service.RuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by laiyijie on 12/27/17.
 */
@Service
public class RuleServiceImpl implements RuleService {
    @Autowired
    private TbRuleRepository tbRuleRepository;
    @Override
    public void deleteRule(Integer ruleId) {
        tbRuleRepository.delete(ruleId);
    }

    @Override
    public void modifyRule(TbRule rule) {
        tbRuleRepository.save(rule);
    }

    @Override
    public TbRule createRule(TbRule rule) {
        return tbRuleRepository.save(rule);
    }
}
