package com.sanzuniao.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanzuniao.member.domain.Member;

/**
 * @author yangguang
 * @description 针对表【member(会员)】的数据库操作Service
 * @createDate 2024-06-26 23:03:23
 */
public interface MemberService extends IService<Member> {

    String test();

    long register(String mobile);
}
