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

@SpringBootApplication(scanBasePackages = "me.laiyijie.job")
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

}
