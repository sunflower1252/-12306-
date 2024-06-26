package com.sanzuniao.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sanzuniao.member.domain.Member;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author yangguang
 * @description 针对表【member(会员)】的数据库操作Mapper
 * @createDate 2024-06-26 23:03:23
 * @Entity generator.domain.Member
 */
@Mapper
public interface MemberMapper extends BaseMapper<Member> {

}




