package me.laiyijie.job.admin.web.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by laiyijie on 12/4/17.
 */
@Component
@EnableScheduling
public class TestSchedule {

    @Autowired
    private SimpMessagingTemplate simp;

    @Scheduled(fixedDelay = 1000)
    public void sendTest(){
        simp.convertAndSend("/topic/test","wttfff");
    }
}
