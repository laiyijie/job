package me.laiyijie.job.admin.service.command;

import me.laiyijie.job.admin.service.mq.JobQueueNameService;
import me.laiyijie.job.message.log.RunningLogMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
@RabbitListener(queues = "#{jobQueueNameService.getLogQueueName()}")
public class LogHandler {
    private Logger log = LoggerFactory.getLogger(LogHandler.class);

    @RabbitHandler
    public void handle(RunningLogMsg runningLogMsg) {
        //TODO need finish
        log.info("receive log:" + runningLogMsg);
    }
}
