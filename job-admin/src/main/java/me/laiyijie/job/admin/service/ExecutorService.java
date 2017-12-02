package me.laiyijie.job.admin.service;

import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;

/**
 * Created by laiyijie on 12/2/17.
 */
public interface ExecutorService {
    void deleteExecutorGroup(String groupName);

    void modifyExecutorGroup(TbExecutorGroup group);

    void createExecutorGroup(TbExecutorGroup group);
}
