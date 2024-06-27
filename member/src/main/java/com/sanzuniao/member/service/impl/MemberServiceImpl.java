package com.sanzuniao.member.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.member.domain.Member;
import com.sanzuniao.member.mapper.MemberMapper;
import com.sanzuniao.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangguang
 * @description 针对表【member(会员)】的数据库操作Service实现
 * @createDate 2024-06-26 23:03:23
 */
@Service
@Slf4j
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member>
        implements MemberService {

    @Autowired
    private MemberMapper memberMapper;


    @Override
    public String test() {
        Member member = memberMapper.selectById(1L);
        log.info("member:{}", member);
        return member.getMobile();
    }

    /**
     * @param mobile 手机号
     * @return 用户id
     */
    @Override
    public long register(String mobile) {
        Member memberByMobile = memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("mobile", mobile));
        if (memberByMobile != null) {
            throw new RuntimeException("手机号已被注册");
        }

        Member member = new Member();
        // 使用系统的时间戳作为用户id
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);

        memberMapper.insert(member);
        return member.getId();
    }


}




