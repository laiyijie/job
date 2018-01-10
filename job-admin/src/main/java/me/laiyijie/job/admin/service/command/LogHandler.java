package me.laiyijie.job.admin.service.command;

import me.laiyijie.job.admin.dao.TbJobErrorLogRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.TbRuleRepository;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobErrorLog;
import me.laiyijie.job.admin.dao.entity.TbRule;
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

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by laiyijie on 11/30/17.
 */
@Component
@RabbitListener(queues = "#{jobQueueNameService.getLogQueueName()}")
@Transactional
public class LogHandler {
    private Logger log = LoggerFactory.getLogger(LogHandler.class);
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private TbJobErrorLogRepository tbJobErrorLogRepository;
    @Autowired
    private TbJobRepository tbJobRepository;
    @Autowired
    private TbRuleRepository tbRuleRepository;
    @RabbitHandler
    public void handle(RunningLogMsg runningLogMsg) {
        simpMessagingTemplate.convertAndSend("/topic/log", runningLogMsg);
        log.debug(runningLogMsg.toString());
        if (runningLogMsg.getError()) {
            TbJobErrorLog tbJobErrorLog = new TbJobErrorLog();
            tbJobErrorLog.setExecutorName(runningLogMsg.getExecutorName());

            tbJobErrorLog.setWorkflowId(runningLogMsg.getWorkFlowId());
            tbJobErrorLog.setJobGroupId(runningLogMsg.getJobGroupId());
            tbJobErrorLog.setJobId(runningLogMsg.getJobId());

            tbJobErrorLog.setContent(runningLogMsg.getContent());
            tbJobErrorLog.setLogTime(runningLogMsg.getTime());
            tbJobErrorLogRepository.save(tbJobErrorLog);

            // retry
            TbJob tbJob = tbJobRepository.findOne(runningLogMsg.getJobId());
            if (tbJob == null)
                return;

            // retry rule
            Iterable<TbRule> rules = tbRuleRepository.findAll();
            if (Objects.equals(true, tbJob.getRetryFlag())) {
                return;
            }
            for (TbRule rule : rules) {
                if (tbJob.getScript() != null && tbJob.getScript().contains(rule.getScript())
                        && RegexUtil.isMatch(rule.getPattern(), runningLogMsg.getContent())) {
                    tbJob.setRuleMaxRetryTimes(rule.getRetryTimes());
                    tbJob.setRetryFlag(true);
                    tbJobRepository.save(tbJob);
                    return;
                }
            }

            //retry own rule
            if (tbJob.getMaxRetryTimes() == null || tbJob.getMaxRetryTimes() == 0)
                return;
            if (tbJob.getMaxRetryTimes().equals(tbJob.getRetryTimes()))
                return;
            if (RegexUtil.isMatch(tbJob.getRetryRegex(), runningLogMsg.getContent())) {
                tbJob.setRetryFlag(true);
                tbJobRepository.save(tbJob);
            }
        }

    }
}
