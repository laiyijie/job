package me.laiyijie.job.executor;

import me.laiyijie.job.executor.service.JobRunnerService;
import me.laiyijie.job.message.executor.RunJobMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by laiyijie on 12/17/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class CommandRunnerTest {
    @Autowired
    private JobRunnerService runnerService;

    @Test
    public void testRun() throws InterruptedException {

        runnerService.runShell(new RunJobMsg(1,1,1,"pwd"));
    }
}
