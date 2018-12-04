package com.lanceyi.small.controller;


import com.lanceyi.small.base.SuccessResponseTemplate;
import com.lanceyi.small.config.Path;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Lance YI
 * @date 2018/12/4 10:01
 */

@RestController
public class TestController {

    @ApiOperation("测试hello")
    @GetMapping(Path.Test.BASE)
    public SuccessResponseTemplate hello(){
        return SuccessResponseTemplate.ok("hello");
    }
}
