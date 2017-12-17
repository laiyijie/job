package me.laiyijie.job.admin.dao;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.entity.TbJobErrorLog;
import me.laiyijie.job.message.log.RunningLogMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by laiyijie on 12/16/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JobErrorLogTest {

    @Autowired
    private TbJobErrorLogRepository repository;
    private Logger log = LoggerFactory.getLogger(JobErrorLogTest.class);
    @Test
    public void testSaveAndGetLog() {
        RunningLogMsg runningLogMsg = new RunningLogMsg(1, 1, 1, "123", true, System.currentTimeMillis(), "first");
        for (int i = 0 ; i <10; i++){
            runningLogMsg.setTime(System.currentTimeMillis()+i);
            runningLogMsg.setContent("conent " + i);
            runningLogMsg.setJobId((i % 5) +1);
            saveLog(runningLogMsg);
        }
        Pageable pageable = new PageRequest(0,5);
//        log.info(JSON.toJSONString(repository.findAllByOrderByLogTimeDesc(pageable),true));
        log.info(JSON.toJSONString(repository.findAllByJobIdOrderByLogTimeDesc(1,pageable),true));
    }


    private void saveLog(RunningLogMsg runningLogMsg) {
        TbJobErrorLog tbJobErrorLog = new TbJobErrorLog();
        tbJobErrorLog.setExecutorName(runningLogMsg.getExecutorName());

        tbJobErrorLog.setWorkflowId(runningLogMsg.getWorkFlowId());
        tbJobErrorLog.setJobGroupId(runningLogMsg.getJobGroupId());
        tbJobErrorLog.setJobId(runningLogMsg.getJobId());

        tbJobErrorLog.setContent(runningLogMsg.getContent());
        tbJobErrorLog.setLogTime(runningLogMsg.getTime());
        repository.save(tbJobErrorLog);
    }
}
