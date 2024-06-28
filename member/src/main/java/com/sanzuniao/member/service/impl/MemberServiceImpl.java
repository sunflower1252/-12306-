package com.sanzuniao.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.common.JwtUtil;
import com.sanzuniao.exception.BusinessException;
import com.sanzuniao.exception.BusinessExceptionEnum;
import com.sanzuniao.member.domain.Member;
import com.sanzuniao.member.mapper.MemberMapper;
import com.sanzuniao.member.req.MemberLoginReq;
import com.sanzuniao.member.req.MemberRegisterReq;
import com.sanzuniao.member.req.MemberSendCodeReq;
import com.sanzuniao.member.resp.MemberLoginResp;
import com.sanzuniao.member.service.MemberService;
import com.sanzuniao.util.SnowUtil;
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
     * 注册
     * @param req 会员封装类
     *            mobile手机号
     * @return 用户id
     */
    @Override
    public long register(MemberRegisterReq req) {
        // 获取手机号检查是否已经注册过
        String mobile = req.getMobile();
        Member memberByMobile = memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("mobile", mobile));
        if (ObjectUtil.isNotNull(memberByMobile)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_EXIST);
        }

        Member member = new Member();
        // 使用雪花算法生成用户id
        member.setId(SnowUtil.getSnowflakeNextId());
        member.setMobile(mobile);
        memberMapper.insert(member);
        return member.getId();
    }


    /**
     * @param req 封装过后的手机号
     *            mobile 手机号
     */
    @Override
    public void sendCode(MemberSendCodeReq req) {
        // 获取手机号检查是否已经注册过
        String mobile = req.getMobile();
        Member memberByMobile = memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("mobile", mobile));
        // 如果手机号不存在，则没注册过，插入记录
        if (ObjectUtil.isNull(memberByMobile)) {
            log.info("手机号不存在，插入一条记录");
            Member member = new Member();
            // 使用雪花算法生成用户id
            member.setId(SnowUtil.getSnowflakeNextId());
            member.setMobile(mobile);
            memberMapper.insert(member);
        } else {
            log.info("手机号存在，不插入记录");
        }
        // 生成验证码
        //String code = RandomUtil.randomString(4);
        //为了生产环境更方便，更改短信验证码
        String code = "1111";
        log.info("生成短信验证码：{}", code);

        // 保存短信记录表：手机号，短信验证码，有效期，是否已使用，业务类型，发送时间，使用时间
        log.info("保存短信记录表");

        // 对接短信通道，发送短信
        log.info("对接短信通道，发送短信");
    }


    /**
     * @param req 封装的登录数据类
     *            mobile 手机号
     *            code 短信验证码
     * @return memberLoginResp 封装的返回数据类
     */
    @Override
    public MemberLoginResp login(MemberLoginReq req) {
        // 通过封装的登录数据类来获取手机号
        String mobile = req.getMobile();
        String code = req.getCode();
        // 根据手机号查询数据库，把用户的信息查出来，包含手机号合用户id
        Member member = memberMapper.selectOne(new QueryWrapper<Member>()
                .eq("mobile", mobile));

        // 手机号不存在，添加一条记录
        if (ObjectUtil.isNull(member)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_NOT_EXIST);
        }

        // 校验验证码
        if (!"1111".equals(code)) {
            throw new BusinessException(BusinessExceptionEnum.MEMBER_MOBILE_CODE_ERROR);
        }

        // 返回封装类，这个封装类是没有隐私信息的，如果返回的数据有隐私信息的东西，我们要写一个封装类来返回
        MemberLoginResp memberLoginResp = BeanUtil.copyProperties(member, MemberLoginResp.class);
        // 生成token，JwtUtil.createToken是封装在common.common包中的
        final String token = JwtUtil.createToken(memberLoginResp.getId(), memberLoginResp.getMobile());
        // 封装类设置token
        memberLoginResp.setToken(token);
        //返回封装的返回数据类
        return memberLoginResp;
    }


}




