package me.laiyijie.job.admin.show;

import me.laiyijie.job.admin.dao.TbExecutorGroupRepository;
import me.laiyijie.job.admin.show.converter.ExecutorConverter;
import me.laiyijie.job.swagger.model.Executor;
import me.laiyijie.job.swagger.model.ExecutorGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by laiyijie on 12/2/17.
 */
@Component
public class ExecutorShow {
    @Autowired
    private TbExecutorGroupRepository tbExecutorGroupRepository;
    @Autowired
    private ExecutorConverter executorConverter;

    public List<ExecutorGroup> getAllExecutorGroup() {
        return tbExecutorGroupRepository.findAll().stream().map((
                tbExecutorGroup -> executorConverter.convertToExecutorGroup(tbExecutorGroup)))
                .collect(Collectors.toList());
    }

    public ExecutorGroup getExecutorGroup(String groupName) {
        return executorConverter.convertToExecutorGroup(tbExecutorGroupRepository.findOne(groupName));
    }


}
