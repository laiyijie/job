package me.laiyijie.job.executor.runner;

import me.laiyijie.job.executor.service.JobQueueService;
import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by laiyijie on 11/29/17.
 */
@EnableScheduling
@Component
public class ExecutorStatusScheduler {
    @Value("${job.executor.group.name}")
    private String groupName;
    @Value("${job.executor.name}")
    private String executorName;
    @Autowired
    private JobQueueService jobQueueService;

    @Scheduled(fixedDelay = 5000)
    public void heartBeat(){
        jobQueueService.sendHeartBeat(new HeartBeatMsg(groupName,executorName));
    }

}
