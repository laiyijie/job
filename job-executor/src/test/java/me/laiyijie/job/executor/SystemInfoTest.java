package me.laiyijie.job.executor;

import com.sun.management.OperatingSystemMXBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryManagerMXBean;

/**
 * Created by laiyijie on 12/16/17.
 */
@RunWith(JUnit4.class)
public class SystemInfoTest {
    @Test
    public void te() {
        System.out.println("yeahs");
        System.out.println("free: " + Runtime.getRuntime().freeMemory());
        System.out.println("max: " + Runtime.getRuntime().maxMemory());
        System.out.println("total: " + Runtime.getRuntime().totalMemory());
        OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        System.out.println("free_physical: " + bean.getFreePhysicalMemorySize());
        System.out.println(" total: " + bean.getTotalPhysicalMemorySize());
        System.out.println("cpuload :" + bean.getSystemCpuLoad());
    }
}
