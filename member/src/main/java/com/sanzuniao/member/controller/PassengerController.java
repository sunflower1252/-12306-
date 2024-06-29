package com.sanzuniao.member.controller;

import com.sanzuniao.context.LoginMemberContext;
import com.sanzuniao.member.req.PassengerListReq;
import com.sanzuniao.member.req.PassengerSaveReq;
import com.sanzuniao.member.service.PassengerService;
import com.sanzuniao.response.Result;
import jakarta.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 乘客信息管理控制器类。
 * 提供乘客信息的保存接口，接收HTTP请求并调用服务层进行业务处理。
 *
 * @author yangguang
 */
@RestController
@RequestMapping("/PassengerController") // 指定基础请求路径
public class PassengerController {

    /**
     * 注入乘客信息服务，用于处理乘客相关的业务逻辑
     */
    @Resource
    private PassengerService passengerService;

    /**
     * 保存乘客信息接口。
     *
     * @param req 乘客保存请求参数，包含待保存的乘客信息。
     * @return Result 成功响应携带"success"消息。
     */
    @GetMapping("/save") // 映射GET请求到/save路径
    public Result save(@RequestBody @Validated PassengerSaveReq req) {
        // 调用乘客服务的保存方法处理请求数据
        passengerService.save(req);
        // 返回成功响应结果
        return Result.success("success");
    }


    @GetMapping("/queryList")
    public Result queryList(@Validated PassengerListReq req) {
        // 这里的id就是会员id，只不过封装类里面只有一个id参数，就写了id没写memberId
        Long id = LoginMemberContext.getId();
        req.setMemberId(id);
        return Result.success(passengerService.quertList(req));
    }
}
