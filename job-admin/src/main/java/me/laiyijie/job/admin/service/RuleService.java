package me.laiyijie.job.admin.service;

import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.dao.entity.TbRule;

/**
 * Created by laiyijie on 12/2/17.
 */
public interface RuleService {
    void deleteRule(Integer ruleId);

    void modifyRule(TbRule rule);

    TbRule createRule(TbRule rule);


}
