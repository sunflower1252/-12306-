package com.sanzuniao.member.controller;

import com.sanzuniao.member.service.MemberService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

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

    /**
     * 注册
     *
     * @param mobile 手机号
     * @return 用户id
     */
    @PostMapping("/register")
    public Long register(@RequestParam String mobile) {
        return memberService.register(mobile);
    }

}
