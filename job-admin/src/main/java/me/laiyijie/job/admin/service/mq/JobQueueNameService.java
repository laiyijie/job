package me.laiyijie.job.admin.service.mq;

import me.laiyijie.job.message.JobQueueName;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/29/17.
 */
@Component
public class JobQueueNameService implements JobQueueName {
    @Value("${job.queue.name.prefix}")
    public String queuePrefix;

    public String getCommandQueueName() {
        return queuePrefix + COMMAND;
    }

    public String getLogQueueName() {
        return queuePrefix + LOG;
    }

    public String getExecutorQueueName(String executorName) {
        return queuePrefix + EXECUTOR_PREFIX + executorName;
    }


}
