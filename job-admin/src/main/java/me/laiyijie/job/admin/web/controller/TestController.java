package me.laiyijie.job.admin.web.controller;

import io.swagger.annotations.ApiParam;
import me.laiyijie.job.swagger.api.TestApi;
import me.laiyijie.job.swagger.model.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by laiyijie on 11/30/17.
 */
@RestController
public class TestController implements TestApi {
    @Override
    public ResponseEntity<TestResponse> testInfoGet(@ApiParam(value = "你想说的话", required = true) @RequestParam(value = "word", required = true) String word, HttpServletRequest request, HttpServletResponse response) throws Exception {
        TestResponse testResponse = new TestResponse();
        testResponse.setMyWord(word);
        testResponse.setCount(1L);
        return ResponseEntity.ok(testResponse);
    }

    @RequestMapping("/")
    public String home() {
        return "wellcome!";
    }
}
