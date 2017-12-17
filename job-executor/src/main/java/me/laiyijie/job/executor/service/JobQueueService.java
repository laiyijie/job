package me.laiyijie.job.executor.service;

import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import me.laiyijie.job.message.command.SystemInfoMsg;
import me.laiyijie.job.message.log.RunningLogMsg;
import org.springframework.amqp.core.AmqpTemplate;
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

    public void sendLog(RunningLogMsg logMsg) {
        amqpTemplate.convertAndSend(jobQueueNameService.getLogQueueName(), logMsg);
    }

    public void sendJobStatus(JobStatusMsg updateJobStatus) {
        amqpTemplate.convertAndSend(jobQueueNameService.getCommandQueueName(), updateJobStatus);
    }

    public void sendHeartBeat(HeartBeatMsg heartBeat) {
        amqpTemplate.convertAndSend(jobQueueNameService.getCommandQueueName(), heartBeat);
    }

    public void sendSystemInfo(SystemInfoMsg systemInfoMsg) {
        amqpTemplate.convertAndSend(jobQueueNameService.getCommandQueueName(), systemInfoMsg);
    }
}
