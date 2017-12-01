package me.laiyijie.job.admin.service.command;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.TbExecutorGroupRepository;
import me.laiyijie.job.admin.dao.TbExecutorRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.entity.TbExecutor;
import me.laiyijie.job.admin.dao.entity.TbExecutorGroup;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.service.mq.JobQueueNameService;
import me.laiyijie.job.message.RunningStatus;
import me.laiyijie.job.message.command.HeartBeatMsg;
import me.laiyijie.job.message.command.JobStatusMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 11/30/17.
 */
@RabbitListener(queues = "#{jobQueueNameService.getCommandQueueName()}")
@Component
public class CommandHandler {
    private Logger log = LoggerFactory.getLogger(CommandHandler.class);

    @Autowired
    private TbExecutorGroupRepository tbExecutorGroupRepository;
    @Autowired
    private TbExecutorRepository tbExecutorRepository;

    @Autowired
    private TbJobRepository tbJobRepository;

    @RabbitHandler
    public void handle(HeartBeatMsg heartBeatMsg) {
        log.debug("heart beat receive : " + heartBeatMsg);
        if (heartBeatMsg.getExecutorName() ==null || heartBeatMsg.getGroupName() == null
                || "".equals(heartBeatMsg.getExecutorName()) || "".equals(heartBeatMsg.getGroupName())){
            return;
        }
        TbExecutorGroup tbExecutorGroup = tbExecutorGroupRepository.findOne(heartBeatMsg.getGroupName());
        // create the group if not exsit
        if (tbExecutorGroup==null){
            tbExecutorGroup = new TbExecutorGroup();
            tbExecutorGroup.setName(heartBeatMsg.getGroupName());
            tbExecutorGroup.setDescription("AUTO_CREATE");
            tbExecutorGroupRepository.save(tbExecutorGroup);
        }

        TbExecutor tbExecutor = tbExecutorRepository.findOne(heartBeatMsg.getExecutorName());

        if (tbExecutor == null){
            tbExecutor = new TbExecutor();
            tbExecutor.setName(heartBeatMsg.getExecutorName());
            tbExecutor.setExecutorGroup(tbExecutorGroup);
            tbExecutor.setOnlineStatus(TbExecutor.ONLINE);
            tbExecutor.setLastHeartBeatTime(System.currentTimeMillis());
            tbExecutorRepository.save(tbExecutor);
        }else {
            tbExecutor.setLastHeartBeatTime(System.currentTimeMillis());
            tbExecutorRepository.save(tbExecutor);
        }
        log.debug("executor group:" + JSON.toJSONString(tbExecutorRepository.findAll()));
    }

    @RabbitHandler
    public void handle(JobStatusMsg jobStatusMsg) {
        //TODO need to finish
        log.debug("job status msg recevive: " + jobStatusMsg);
        TbJob tbJob = tbJobRepository.findOne(jobStatusMsg.getJobId());
        if (tbJob == null)
            return;
        if (RunningStatus.FINISHED.equals(tbJob.getStatus()) ||
                RunningStatus.FAILED.equals(tbJob.getStatus())){
            return;
        }
        tbJob.setStatus(jobStatusMsg.getStatus());
        tbJobRepository.save(tbJob);
    }


}
