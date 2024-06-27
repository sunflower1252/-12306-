package com.sanzuniao.member.controller;

import com.sanzuniao.member.req.MemberRegisterReq;
import com.sanzuniao.member.service.MemberService;
import com.sanzuniao.response.Result;
import jakarta.annotation.Resource;
import jakarta.validation.Valid;
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
     * @param req 会员封装类
     * @return 用户id
     */
    @PostMapping("/register")
    public Result register(@Valid @RequestBody MemberRegisterReq req) {
        return Result.success(memberService.register(req));
    }

}
