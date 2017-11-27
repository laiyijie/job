package me.laiyijie.job.admin.dao;

import me.laiyijie.job.admin.dao.entity.Job;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by laiyijie on 11/27/17.
 */
public interface JobRepository extends CrudRepository<Job, Integer> {
    List<Job> findAll();
}
