package me.laiyijie.job.executor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by laiyijie on 11/29/17.
 */
@RestController
public class MainController {

    @RequestMapping("/")
    public String home(){
        return "I'm running";
    }
}
