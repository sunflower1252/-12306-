package com.sanzuniao.member.service.impl;

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
}




