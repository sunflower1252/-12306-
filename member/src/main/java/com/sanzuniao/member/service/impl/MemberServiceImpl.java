package com.sanzuniao.member.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.exception.BusinessException;
import com.sanzuniao.exception.BusinessExceptionEnum;
import com.sanzuniao.member.domain.Member;
import com.sanzuniao.member.mapper.MemberMapper;
import com.sanzuniao.member.req.MemberRegisterReq;
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
     * @param req 会员封装类
     * @return 用户id
     */
    @Override
    public long register(MemberRegisterReq req) {
        // 获取手机号检查是否已经注册过
        String mobile = req.getMobile();
        Member memberByMobile = memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("mobile", mobile));
        if (ObjectUtil.isNotEmpty(memberByMobile)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        // 使用系统的时间戳作为用户id
        member.setId(System.currentTimeMillis());
        member.setMobile(mobile);

        memberMapper.insert(member);
        return member.getId();
    }


}




