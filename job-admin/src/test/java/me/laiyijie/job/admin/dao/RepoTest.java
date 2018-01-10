package me.laiyijie.job.admin.dao;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.entity.TbJob;
import me.laiyijie.job.admin.dao.entity.TbJobGroup;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.message.RunningStatus;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by laiyijie on 11/28/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RepoTest {

    private static final Logger log = LoggerFactory.getLogger(RepoTest.class);

    @Resource
    private TbJobRepository tbJobRepository;
    @Resource
    private TbJobGroupRepository tbJobGroupRepository;
    @Resource
    private TbWorkFlowRepository tbWorkFlowRepository;

    @Autowired
    private Flyway flyway;

    @Before
    public void clearAndAddWorkFlow() {
        flyway.clean();
        flyway.migrate();
        TbWorkFlow tbWorkFlow = new TbWorkFlow();
        tbWorkFlow.setId(1);
        tbWorkFlow.setName("first work flow");
        tbWorkFlow.setRunInterval(0);
        tbWorkFlow.setStatus(RunningStatus.INIT);
        tbWorkFlowRepository.save(tbWorkFlow);
    }

    @Test
    public void testMultiJobGroup() {
        log.info(JSON.toJSONString(tbWorkFlowRepository.findOne(1)));
        TbJobGroup tbJobGroup = new TbJobGroup();
        tbJobGroup.setId(1);
        tbJobGroup.setName("1st");
        tbJobGroup.setWorkFlow(tbWorkFlowRepository.findOne(1));
        tbJobGroupRepository.save(tbJobGroup);

        TbJobGroup tbJobGroup1 = new TbJobGroup();
        tbJobGroup1.setId(2);
        tbJobGroup1.setName("2nd");
        tbJobGroup1.setWorkFlow(tbWorkFlowRepository.findOne(1));
        tbJobGroup1.setStep(1);
        tbJobGroupRepository.save(tbJobGroup1);

        TbJob tbJob = new TbJob();
        tbJob.setName("1st");
        tbJob.setJobGroup(tbJobGroup);
        tbJobRepository.save(tbJob);
        log.info(JSON.toJSONString(tbJobGroupRepository.findAllByWorkFlow_Id(1)));
        log.info(JSON.toJSONString(tbJobRepository.findALlByJobGroup_WorkFlow_id(1)));

        log.info(JSON.toJSONString(tbJobGroupRepository.findAll()));
    }

    @Test
    public void testSaveJob(){
        TbJob tbJob = new TbJob();
        tbJob.setName("ooo");
        tbJobRepository.save(tbJob);

        log.error(JSON.toJSONString(tbJobRepository.findAll()));
    }

}
