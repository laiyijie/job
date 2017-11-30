package me.laiyijie.job.executor.config;

import me.laiyijie.job.executor.service.JobQueueNameService;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by laiyijie on 11/29/17.
 */
@Configuration
public class RabbitMqConfig {
    @Autowired
    private JobQueueNameService service;

    @Bean
    public Queue commandQueue(){
        return new Queue(service.getCommandQueueName(),true);
    }
    @Bean
    public Queue executorQueue(){
        return new Queue(service.getExecutorQueueName(),true,false,true);
    }

}
