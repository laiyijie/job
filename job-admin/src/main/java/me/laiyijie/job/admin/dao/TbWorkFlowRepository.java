package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by laiyijie on 11/27/17.
 */

public interface TbWorkFlowRepository extends CrudRepository<TbWorkFlow, Integer> {
    List<TbWorkFlow> findAll();
    List<TbWorkFlow> findAllByStatus(String status);
}
