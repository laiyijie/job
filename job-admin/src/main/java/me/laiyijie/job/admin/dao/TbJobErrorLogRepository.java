package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.TbJobErrorLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by laiyijie on 12/16/17.
 */
public interface TbJobErrorLogRepository extends CrudRepository<TbJobErrorLog, Long> {

    List<TbJobErrorLog> findAllByOrderByLogTimeDesc(Pageable pageable);

    List<TbJobErrorLog> findAllByJobIdOrderByLogTimeDesc(Integer jobId, Pageable pageable);
}
