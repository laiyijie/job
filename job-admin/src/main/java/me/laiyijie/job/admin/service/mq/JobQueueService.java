package me.laiyijie.job.admin.service.mq;

import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
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

    public void sendMessageToCommandQueue(Object msg) {
        amqpTemplate.convertAndSend(jobQueueNameService.getCommandQueueName(), msg);
    }


}
