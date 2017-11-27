package me.laiyijie.job.admin;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.WorkFlowRepository;
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
    public CommandLineRunner demo(WorkFlowRepository repository) {
        return (args) -> {
            // save a couple of customers
            repository.save(new WorkFlow("dasazi","jiushi sa"));

            log.info("FFFFFF");
            log.info(JSON.toJSONString(repository.findAll()));
        };
    }
}
