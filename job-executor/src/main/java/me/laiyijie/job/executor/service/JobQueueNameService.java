package me.laiyijie.job.executor.service;

import me.laiyijie.job.message.JobQueueName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/29/17.
 */
@Component
public class JobQueueNameService {
    @Value("${job.queue.name.prefix}")
    private String queuePrefix;
    @Value("${job.executor.name}")
    private String executorName;

    public String getLogQueueName() {
        return queuePrefix + JobQueueName.LOG;
    }

    public String getCommandQueueName() {
        return queuePrefix + JobQueueName.COMMAND;
    }

    public String getExecutorQueueName() {
        return queuePrefix + JobQueueName.EXECUTOR_PREFIX + executorName;
    }
}
