package me.laiyijie.job.admin;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.TbJobGroupRepository;
import me.laiyijie.job.admin.dao.TbJobRepository;
import me.laiyijie.job.admin.dao.TbWorkFlowRepository;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {
    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

    @Bean
    public CommandLineRunner demo(TbWorkFlowRepository workFlowRepository, TbJobGroupRepository jobGroupRepository, TbJobRepository jobRepository) {
        return (args) -> {
            // save a couple of customers
            workFlowRepository.save(new TbWorkFlow("dasazi", "jiushi sa"));
            TbJobGroup jobGroup = new TbJobGroup();
            jobGroup.setName("group");
            jobGroup.setWorkFlow(workFlowRepository.findOne(1));
            jobGroupRepository.save(jobGroup);
            TbJob job = new TbJob();
            job.setName("myjob");
            job.setDescription("just test jobs");
            job.setJobGroup(jobGroupRepository.findOne(1));
            jobRepository.save(job);
            log.info(JSON.toJSONString(job));

            jobGroup = new TbJobGroup();
            jobGroup.setName("123");
//            jobGroup.setPreJobGroup(jobGroupRepository.findOne(1));
//            jobGroup.setWorkFlow(workFlowRepository.findOne(1));
            jobGroupRepository.save(jobGroup);
            log.info(JSON.toJSONString(jobGroup));


            job = new TbJob();
            job.setName("123job");
            job.setJobGroup(jobGroupRepository.findOne(2));
            jobRepository.save(job);

            log.info("FFFFFF");
            log.info(JSON.toJSONString(jobRepository.findAll()));
        };
    }
}
