package me.laiyijie.job.admin;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.TbExecutorGroupRepository;
import me.laiyijie.job.admin.dao.TbJobGroupRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.TbWorkFlowRepository;
import me.laiyijie.job.admin.dao.entity.*;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.message.RunningStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Autowired
    private TbWorkFlowRepository tbWorkFlowRepository;
    @Autowired
    private TbJobRepository tbJobRepository;
    @Autowired
    private TbJobGroupRepository tbJobGroupRepository;
    @Autowired
    private TbExecutorGroupRepository tbExecutorGroupRepository;
    @Autowired
    private WorkFlowService workFlowService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(TbWorkFlowRepository workFlowRepository, TbJobGroupRepository jobGroupRepository, TbJobRepository jobRepository) {
        return (args) -> {

            TbWorkFlow tbWorkFlow = new TbWorkFlow();
            tbWorkFlow.setId(1);
            tbWorkFlow.setName("first work flow");
            tbWorkFlow.setRunInterval(0);
            tbWorkFlow.setStatus(RunningStatus.INIT);
            tbWorkFlowRepository.save(tbWorkFlow);

            TbJobGroup tbJobGroup = new TbJobGroup();
            tbJobGroup.setId(1);
            tbJobGroup.setName("1st");
            tbJobGroup.setWorkFlow(tbWorkFlowRepository.findOne(1));
            tbJobGroup.setStep(1);
            tbJobGroupRepository.save(tbJobGroup);

            TbExecutorGroup tbExecutorGroup = new TbExecutorGroup();
            tbExecutorGroup.setName("default");
            tbExecutorGroupRepository.save(tbExecutorGroup);

            TbJob tbJob = new TbJob();
            tbJob.setName("1st");
            tbJob.setJobGroup(tbJobGroup);
            tbJob.setExecutorGroup(tbExecutorGroup);
            tbJob.setScript("ls");
            tbJobRepository.save(tbJob);
            Executors.newSingleThreadExecutor().execute(()->{
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.debug("run work flow !!!!!!!! here !!!!! why no !!!!");
                workFlowService.runWorkFlow(1);
            });
        };
    }
}
