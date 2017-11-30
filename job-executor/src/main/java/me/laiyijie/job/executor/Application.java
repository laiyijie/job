package me.laiyijie.job.executor;

import me.laiyijie.job.executor.service.JobRunnerService;
import me.laiyijie.job.message.executor.RunJobMsg;
import me.laiyijie.job.message.executor.StopJobMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


/**
 * Created by laiyijie on 11/29/17.
 */
@SpringBootApplication
public class Application {
    private Logger log = LoggerFactory.getLogger(Application.class);
    @Value("${job.executor.name}")
    public String EXECUTOR_NAME;
    @Autowired
    private JobRunnerService jobRunnerService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class);
    }

//    @Bean
//    public CommandLineRunner runner() {
//        return (args) -> {
//            log.info(EXECUTOR_NAME);
//            jobRunnerService.runShell(new RunJobMsg(1, "#!/bin/sh\nls\nsleep 10\nls\n"));
//            Thread.sleep(2000);
//            jobRunnerService.stopShell(new StopJobMsg(1));
//        };
//    }
}
