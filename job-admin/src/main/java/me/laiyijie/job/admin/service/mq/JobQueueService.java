package me.laiyijie.job.admin.service.mq;

import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.executor.StopJobMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/29/17.
 */
@Component
public class JobQueueService {
    @Autowired
    private JobQueueNameService jobQueueNameService;
    @Autowired
    private AmqpTemplate amqpTemplate;
    private Logger log = LoggerFactory.getLogger(JobQueueService.class);
    public void sendRunJobToExecutor(String executorName, RunJobMsg jobMsg) {
        log.error("sending run job executor: " + executorName + " job_msg: " + jobMsg);
        amqpTemplate.convertAndSend(jobQueueNameService.getExecutorQueueName(executorName), jobMsg);
    }

    public void sendStopJobToExecutor(String executorName, StopJobMsg jobMsg) {
        log.error("sending stop job executor: " + executorName + " job_msg: " + jobMsg);
        amqpTemplate.convertAndSend(jobQueueNameService.getExecutorQueueName(executorName), jobMsg);
    }
}
