package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbRule;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by laiyijie on 12/27/17.
 */
public interface TbRuleRepository extends CrudRepository<TbRule,Integer> {
    List<TbRule> findAll();
}
