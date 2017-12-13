package me.laiyijie.job.message.util;

/**
 * Created by admin on 2017/12/13.
 */
public class OsChecker {


    public static OsType getOsType() {
        String osName = System.getProperty("os.name");
        if (osName.contains("Linux"))
            return OsType.Linux;
        if (osName.contains("Windows"))
            return OsType.Windows;
        return OsType.Unknown;
    }
}
