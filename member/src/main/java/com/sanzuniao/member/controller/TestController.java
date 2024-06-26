package com.sanzuniao.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangguang
 */
@RestController
public class TestController {
    @GetMapping("/hello")
    public String test(){
        return "helloWorld";
    }
}
