package me.laiyijie.job.admin.service.command;

import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/30/17.
 */
@RabbitListener
@Component
public class CommandHandler {

    @RabbitHandler
    public void handle(HeartBeatMsg heartBeatMsg) {
        //TODO need to finish
    }

    @RabbitHandler
    public void handle(JobStatusMsg jobStatusMsg) {
        //TODO need to finish

    }


}
