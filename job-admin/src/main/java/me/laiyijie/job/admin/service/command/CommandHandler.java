package me.laiyijie.job.admin.service.command;

import me.laiyijie.job.admin.Application;
import me.laiyijie.job.admin.service.mq.JobQueueNameService;
import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/30/17.
 */
@RabbitListener(queues = "#{jobQueueNameService.getCommandQueueName()}")
@Component
public class CommandHandler {
    private Logger log = LoggerFactory.getLogger(CommandHandler.class);
    @Autowired
    private JobQueueNameService jobQueueNameService;
    @RabbitHandler
    public void handle(HeartBeatMsg heartBeatMsg) {
        //TODO need to finish
        log.info("heart beat receive : " + heartBeatMsg);
    }

    @RabbitHandler
    public void handle(JobStatusMsg jobStatusMsg) {
        //TODO need to finish
        log.info("job status msg recevive: " + jobStatusMsg);
    }


}
