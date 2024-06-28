package com.sanzuniao.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanzuniao.member.domain.Member;
import com.sanzuniao.member.req.MemberLoginReq;
import com.sanzuniao.member.req.MemberRegisterReq;
import com.sanzuniao.member.req.MemberSendCodeReq;
import com.sanzuniao.member.resp.MemberLoginResp;

/**
 * @author yangguang
 * @description 针对表【member(会员)】的数据库操作Service
 * @createDate 2024-06-26 23:03:23
 */
public interface MemberService extends IService<Member> {

    String test();

    long register(MemberRegisterReq req);

    void sendCode(MemberSendCodeReq req);

    MemberLoginResp login(MemberLoginReq req);
}
