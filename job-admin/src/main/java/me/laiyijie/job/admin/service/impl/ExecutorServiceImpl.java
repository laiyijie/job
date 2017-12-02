package me.laiyijie.job.admin.service.impl;

import me.laiyijie.job.admin.dao.TbExecutorGroupRepository;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.service.ExecutorService;
import me.laiyijie.job.admin.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class ExecutorServiceImpl implements ExecutorService {
    @Autowired
    private TbExecutorGroupRepository tbExecutorGroupRepository;

    @Override
    public void deleteExecutorGroup(String groupName) {
        tbExecutorGroupRepository.delete(groupName);
    }

    @Override
    public void modifyExecutorGroup(TbExecutorGroup group) {
        TbExecutorGroup executorGroup = tbExecutorGroupRepository.findOne(group.getName());
        if (executorGroup == null)
            throw new BusinessException("group not exist");
        if (group.getDescription() != null)
            executorGroup.setDescription(group.getDescription());
        tbExecutorGroupRepository.save(group);
    }

    @Override
    public void createExecutorGroup(TbExecutorGroup group) {
        TbExecutorGroup executorGroup = tbExecutorGroupRepository.findOne(group.getName());
        if (executorGroup != null)
            throw new BusinessException("already exist");
        tbExecutorGroupRepository.save(group);
    }
}
