package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.stream.Stream;

/**
 * Created by laiyijie on 11/27/17.
 */
public interface TbJobGroupRepository extends CrudRepository<TbJobGroup, Integer> {
    List<TbJobGroup> findAll();

    List<TbJobGroup> findAllByWorkFlow_Id(Integer workFlowId);

    List<TbJobGroup> findAllByWorkFlow_IdOrderByStep(Integer workFlowId);

}
