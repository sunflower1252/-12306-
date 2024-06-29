package com.sanzuniao.member.controller;

import com.sanzuniao.context.LoginMemberContext;
import com.sanzuniao.member.req.PassengerDelReq;
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
 * 控制器类，负责处理乘客信息的CRUD操作请求，
 * 通过与{@link PassengerService}交互实现业务逻辑。
 * 支持保存乘客信息、查询乘客列表、删除乘客记录及查询当前登录用户的乘客信息等功能。
 *
 * @author yangguang
 * @since [版本号]
 */
@RestController
@RequestMapping("/PassengerController") // 组件的基础请求映射路径
public class PassengerController {

    /**
     * 注入乘客信息服务，用于执行乘客数据的相关操作。
     */
    @Resource
    private PassengerService passengerService;

    /**
     * 保存乘客信息。
     * <p>
     * 接收HTTP GET请求，从请求体中读取并验证{@link PassengerSaveReq}对象，
     * 然后将乘客信息保存至数据库。
     *
     * @param req 包含乘客信息的保存请求参数
     * @return 成功响应，消息内容为"success"
     */
    @GetMapping("/save")
    public Result save(@RequestBody @Validated PassengerSaveReq req) {
        passengerService.save(req);
        return Result.success("success");
    }

    /**
     * 查询乘客列表。
     * <p>
     * 根据当前登录会员ID（从{@link LoginMemberContext}获取），
     * 查询并返回该会员关联的所有乘客信息列表。
     *
     * @param req 乘客列表查询请求参数，会员ID在方法内自动设置
     * @return 成功响应，数据部分为乘客列表
     */
    @GetMapping("/queryList")
    public Result queryList(@Validated PassengerListReq req) {
        Long memberId = LoginMemberContext.getId();
        req.setMemberId(memberId);
        return Result.success(passengerService.queryList(req));
    }

    /**
     * 删除指定乘客记录。
     * <p>
     * 根据请求体中的{@link PassengerDelReq}对象删除乘客信息。
     *
     * @param req 包含待删除乘客标识的请求参数
     * @return 成功响应，消息内容为"success"
     */
    @GetMapping("/delete")
    public Result delete(@Validated @RequestBody PassengerDelReq req) {
        passengerService.delete(req);
        return Result.success("success");
    }

    /**
     * 查询当前登录用户的乘客信息。
     * <p>
     * 无需额外参数，直接返回当前登录用户的乘客详情。
     *
     * @return 成功响应，数据部分为当前用户的乘客信息
     */
    @GetMapping("/queryMine")
    public Result queryMine() {
        return Result.success(passengerService.queryMine());
    }
}
