package me.laiyijie.job.admin.service.util;

import java.util.regex.Pattern;

/**
 * Created by laiyijie on 12/17/17.
 */
public class RegexUtil {

    public static boolean isMatch(String regex,String content){
        return Pattern.compile(regex).matcher(content).matches();
    }
}
