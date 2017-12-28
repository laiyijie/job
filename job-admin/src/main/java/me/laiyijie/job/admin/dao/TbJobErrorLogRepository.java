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

    Page<TbJobErrorLog> findAllByOrderByLogTimeDesc(Pageable pageable);

    Page<TbJobErrorLog> findAllByJobIdOrderByLogTimeDesc(Integer jobId, Pageable pageable);

    Page<TbJobErrorLog> findAllByJobIdAndLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(Integer jobId, Long maxTime, Long minTime, Pageable pageable);

    Page<TbJobErrorLog> findAllByWorkflowIdAndLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(Integer workflowId, Long maxTime, Long minTime, Pageable pageable);

    Page<TbJobErrorLog> findAllByLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(Long maxTime, Long minTime, Pageable pageable);

    Page<TbJobErrorLog> findAllByJobIdAndWorkflowIdAndLogTimeLessThanAndLogTimeGreaterThanOrderByLogTimeDesc(Integer jobId, Integer workflowId, Long maxTime, Long minTime, Pageable pageable);
}
