package me.laiyijie.job.admin;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.JobGroupRepository;
import me.laiyijie.job.admin.dao.JobRepository;
import me.laiyijie.job.admin.dao.WorkFlowRepository;
import me.laiyijie.job.admin.dao.entity.Job;
import me.laiyijie.job.admin.dao.entity.JobGroup;
import me.laiyijie.job.admin.dao.entity.WorkFlow;
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
    public CommandLineRunner demo(WorkFlowRepository workFlowRepository, JobGroupRepository jobGroupRepository, JobRepository jobRepository) {
        return (args) -> {
            // save a couple of customers
            workFlowRepository.save(new WorkFlow("dasazi", "jiushi sa"));
            JobGroup jobGroup = new JobGroup();
            jobGroup.setName("group");
            jobGroup.setWorkFlow(workFlowRepository.findOne(1));
            jobGroupRepository.save(jobGroup);
            Job job = new Job();
            job.setName("myjob");
            job.setDescription("just test jobs");
            job.setJobGroup(jobGroupRepository.findOne(1));
            jobRepository.save(job);

            log.info("FFFFFF");
            log.info(JSON.toJSONString(jobRepository.findAll()));
        };
    }
}
