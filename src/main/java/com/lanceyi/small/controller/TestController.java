package com.lanceyi.small.controller;


import com.lanceyi.small.base.SuccessResponseTemplate;
import com.lanceyi.small.config.Path;
import com.lanceyi.small.dto.response.TestResponse;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Lance YI
 * @date 2018/12/4 10:01
 */

@RestController
public class TestController {

    @ApiOperation("测试")
    @GetMapping(Path.Test.BASE)
    public SuccessResponseTemplate<List<TestResponse>> hello(){
        TestResponse testResponse = new TestResponse();
        testResponse.setFirstName("Yi");
        testResponse.setLastName("Lance");
        return SuccessResponseTemplate.ok(testResponse);
    }
}
