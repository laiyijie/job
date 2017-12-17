package me.laiyijie.job.admin.service.command;

import me.laiyijie.job.admin.dao.TbJobErrorLogRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobErrorLog;
import me.laiyijie.job.admin.service.mq.JobQueueNameService;
import me.laiyijie.job.admin.service.util.RegexUtil;
import me.laiyijie.job.message.log.RunningLogMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
@RabbitListener(queues = "#{jobQueueNameService.getLogQueueName()}")
public class LogHandler {
    private Logger log = LoggerFactory.getLogger(LogHandler.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private TbJobErrorLogRepository tbJobErrorLogRepository;
    @Autowired
    private TbJobRepository tbJobRepository;

    @RabbitHandler
    public void handle(RunningLogMsg runningLogMsg) {
        simpMessagingTemplate.convertAndSend("/topic/log", runningLogMsg);
        log.info(runningLogMsg.toString());
        if (runningLogMsg.getError()) {
            TbJobErrorLog tbJobErrorLog = new TbJobErrorLog();
            tbJobErrorLog.setExecutorName(runningLogMsg.getExecutorName());

            tbJobErrorLog.setWorkflowId(runningLogMsg.getWorkFlowId());
            tbJobErrorLog.setJobGroupId(runningLogMsg.getJobGroupId());
            tbJobErrorLog.setJobId(runningLogMsg.getJobId());

            tbJobErrorLog.setContent(runningLogMsg.getContent());
            tbJobErrorLog.setLogTime(runningLogMsg.getTime());
            tbJobErrorLogRepository.save(tbJobErrorLog);
            TbJob tbJob = tbJobRepository.findOne(runningLogMsg.getJobId());
            if (tbJob == null)
                return;
            if (tbJob.getMaxRetryTimes() == 0)
                return;
            if (Objects.equals(tbJob.getRetryTimes(), tbJob.getMaxRetryTimes()))
                return;
            if (RegexUtil.isMatch(tbJob.getRetryRegex(), runningLogMsg.getContent())) {
                tbJob.setRetryFlag(true);
                tbJobRepository.save(tbJob);
            }
        }

    }
}
