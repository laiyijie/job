package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by laiyijie on 11/30/17.
 */
public interface TbExecutorRepository extends CrudRepository<TbExecutor, String> {

    List<TbExecutor> findAllByExecutorGroup_Id(String gourpId);
}
