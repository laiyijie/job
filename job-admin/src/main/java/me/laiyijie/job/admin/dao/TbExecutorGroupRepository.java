package me.laiyijie.job.admin.dao;

import org.springframework.data.repository.CrudRepository;

import java.util.concurrent.Executor;

/**
 * Created by laiyijie on 11/30/17.
 */
public interface TbExecutorGroupRepository extends CrudRepository<Executor, String> {

}
