package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


/**
 * Created by laiyijie on 11/30/17.
 */
public interface TbExecutorGroupRepository extends CrudRepository<TbExecutorGroup, String> {
    List<TbExecutorGroup> findAll();
}
