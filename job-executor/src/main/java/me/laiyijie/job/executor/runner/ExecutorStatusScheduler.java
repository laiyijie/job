package me.laiyijie.job.executor.runner;

import com.sun.management.OperatingSystemMXBean;
import me.laiyijie.job.executor.service.JobQueueService;
import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import me.laiyijie.job.message.command.SystemInfoMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
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

    private Logger log = LoggerFactory.getLogger(ExecutorStatusScheduler.class);
    @Autowired
    private JobQueueService jobQueueService;

    @Scheduled(fixedDelay = 5000)
    public void heartBeat() {
        jobQueueService.sendHeartBeat(new HeartBeatMsg(groupName, executorName));
    }

    @Scheduled(fixedDelay = 5000)
    public void systemInfo() {
        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        SystemInfoMsg infoMsg = new SystemInfoMsg();
        infoMsg.setCpuLoad(bean.getSystemCpuLoad());
        infoMsg.setFreePhysicalMemory(bean.getFreePhysicalMemorySize());
        infoMsg.setTotalPhysicalMemory(bean.getTotalPhysicalMemorySize());
        infoMsg.setGroupName(groupName);
        infoMsg.setExecutorName(executorName);
        jobQueueService.sendSystemInfo(infoMsg);
        log.info(infoMsg.toString());
    }
}
