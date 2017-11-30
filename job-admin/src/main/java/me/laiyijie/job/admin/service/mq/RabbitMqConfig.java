package me.laiyijie.job.admin.service.mq;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/28/17.
 */
@Configuration
public class RabbitMqConfig {
    @Autowired
    private JobQueueNameService jobQueueNameService;

    @Bean
    public Queue commandQueue(){
        return  new Queue(jobQueueNameService.getCommandQueueName());
    }

    @Bean
    public Queue logQueue(){
        return new Queue(jobQueueNameService.getLogQueueName());
    }
}
