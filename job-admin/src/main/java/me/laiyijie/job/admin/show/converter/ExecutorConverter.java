package me.laiyijie.job.admin.show.converter;

import me.laiyijie.job.admin.dao.TbExecutorRepository;
import me.laiyijie.job.admin.dao.entity.TbExecutor;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.swagger.model.Executor;
import me.laiyijie.job.swagger.model.ExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class ExecutorConverter {
    @Autowired
    private TbExecutorRepository tbExecutorRepository;

    public Executor convertToExecutor(TbExecutor tbExecutor) {
        if (tbExecutor == null)
            return null;
        Executor executor = new Executor();
        executor.setOnlineStatus(tbExecutor.getOnlineStatus());
        executor.setName(tbExecutor.getName());
        executor.setIpAddress(tbExecutor.getIpAddress());
        return executor;
    }

    public ExecutorGroup convertToExecutorGroup(TbExecutorGroup tbExecutorGroup) {
        if (tbExecutorGroup == null) return null;

        ExecutorGroup executorGroup = new ExecutorGroup();
        executorGroup.setName(tbExecutorGroup.getName());
        executorGroup.setDescription(tbExecutorGroup.getDescription());
        executorGroup.setExecutors(
                tbExecutorRepository.findAllByExecutorGroup_Name(tbExecutorGroup.getName())
                        .stream().map((this::convertToExecutor)).collect(Collectors.toList()));
        return executorGroup;
    }

    public ExecutorGroup convertToExecutorGroupWithoutExecutors(TbExecutorGroup tbExecutorGroup) {
        if (tbExecutorGroup == null) return null;

        ExecutorGroup executorGroup = new ExecutorGroup();
        executorGroup.setName(tbExecutorGroup.getName());
        executorGroup.setDescription(tbExecutorGroup.getDescription());
        return executorGroup;
    }
}
