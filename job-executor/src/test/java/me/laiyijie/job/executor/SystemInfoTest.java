package me.laiyijie.job.executor;

import com.sun.management.OperatingSystemMXBean;
import me.laiyijie.job.message.command.JobStatusMsg;
import me.laiyijie.job.message.executor.StopJobMsg;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteWatchdog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by laiyijie on 12/16/17.
 */
@RunWith(JUnit4.class)
public class SystemInfoTest {
    @Test
    public void te() throws InterruptedException {
        System.out.println("yeahs");
        System.out.println("free: " + Runtime.getRuntime()
                                             .freeMemory());
        System.out.println("max: " + Runtime.getRuntime()
                                            .maxMemory());
        System.out.println("total: " + Runtime.getRuntime()
                                              .totalMemory());
        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("free_physical: " + bean.getFreePhysicalMemorySize());
        System.out.println(" total: " + bean.getTotalPhysicalMemorySize());
        System.out.println("cpuload :" + bean.getSystemCpuLoad());


    }

    @Test
    public void t(){
        List<StopJobMsg> stopJobMsgList =  new ArrayList<>();
        for (int i = 0; i<100; i++){
            stopJobMsgList.add(new StopJobMsg(i));
        }
        stopJobMsgList.sort(Comparator.comparingInt(StopJobMsg::getJobId));
        System.out.println(stopJobMsgList.get(0).getJobId());
    }

    @Test
    public void testCommonRunner() throws IOException, InterruptedException {
        String file_name = "tmp_job_1513568423363.bat";
        CommandLine commandLine = new CommandLine(file_name);
        DefaultExecuteResultHandler defaultExecuteResultHandler = new DefaultExecuteResultHandler();
        DefaultExecutor defaultExecutor = new DefaultExecutor();
        defaultExecutor.execute(commandLine,defaultExecuteResultHandler);
        Thread.sleep(2000);
        Thread.sleep(10000);
        Executors.newCachedThreadPool().execute(()->{

        });
    }
}
