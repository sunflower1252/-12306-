package com.sanzuniao.member.controller;

import com.sanzuniao.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangguang
 */
@RestController
@RequestMapping(value = "/MemberController")
public class MemberController {

    @Resource
    private MemberService memberService;


    @GetMapping("/test")
    public String test() {
        return memberService.test();
    }

}
