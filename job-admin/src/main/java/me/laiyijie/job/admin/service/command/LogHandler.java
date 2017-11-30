package me.laiyijie.job.admin.service.command;

import me.laiyijie.job.message.log.RunningLogMsg;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
@RabbitListener
public class LogHandler {
    @RabbitHandler
    public void handle(RunningLogMsg runningLogMsg){
        //TODO need finish

    }
}
