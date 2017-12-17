package me.laiyijie.job.executor.runner;

import me.laiyijie.job.executor.service.JobRunnerService;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.executor.StopJobMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 12/1/17.
 */
@Component
@RabbitListener(queues = "#{jobQueueNameService.getExecutorQueueName()}")
public class JobHandler {
    private Logger log = LoggerFactory.getLogger(JobHandler.class);
    @Autowired
    private JobRunnerService jobRunnerService;
    @RabbitHandler
    public void handle(RunJobMsg runJobMsg){
        log.debug("receive run job : " + runJobMsg);
        jobRunnerService.runShell(runJobMsg);
    }

    @RabbitListener
    public void handle(StopJobMsg stopJobMsg){
        jobRunnerService.stopShell(stopJobMsg);
        log.debug("receive stop job : " + stopJobMsg);
    }
}
