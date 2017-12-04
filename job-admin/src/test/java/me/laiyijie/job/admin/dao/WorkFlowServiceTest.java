package me.laiyijie.job.admin.dao;

import com.alibaba.fastjson.JSON;
import me.laiyijie.job.admin.dao.entity.TbWorkFlow;
import me.laiyijie.job.admin.service.WorkFlowService;
import me.laiyijie.job.admin.show.converter.WorkFlowConverter;
import me.laiyijie.job.message.RunningStatus;
import me.laiyijie.job.swagger.model.JobGroup;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by laiyijie on 12/4/17.
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class WorkFlowServiceTest {

    @Resource
    private TbJobRepository tbJobRepository;
    @Resource
    private TbJobGroupRepository tbJobGroupRepository;
    @Resource
    private TbWorkFlowRepository tbWorkFlowRepository;

    @Autowired
    private WorkFlowService workFlowService;
    @Autowired
    private WorkFlowConverter workFlowConverter;

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
    public void testCreateJobGroup() {
        JobGroup jobGroup = new JobGroup();
        jobGroup.setDescription("ttt");
        jobGroup.setName("fuxk");
        jobGroup.setWorkFlowId(1);
        jobGroup.setStep(10);
        jobGroup.setId(123);
        workFlowService.createJobGroup(workFlowConverter.convertToTbJobGroup(jobGroup));

        System.out.println(JSON.toJSONString(tbJobGroupRepository.findAll()));
    }
}
