package com.sanzuniao.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.context.LoginMemberContext;
import com.sanzuniao.member.domain.Passenger;
import com.sanzuniao.member.mapper.PassengerMapper;
import com.sanzuniao.member.req.PassengerDelReq;
import com.sanzuniao.member.req.PassengerListReq;
import com.sanzuniao.member.req.PassengerSaveReq;
import com.sanzuniao.member.resp.PassengerListResp;
import com.sanzuniao.member.resp.PassengerQueryResp;
import com.sanzuniao.member.service.PassengerService;
import com.sanzuniao.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 实现了{@link PassengerService}接口，针对数据库表【passenger】提供具体操作服务。
 * 使用MyBatis Plus的{@link ServiceImpl}简化CRUD操作实现。
 *
 * @author yangguang
 * @since 2024-06-28
 */
@Service
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger> implements PassengerService {

    @Resource
    private PassengerMapper passengerMapper;

    /**
     * 保存乘客信息至数据库。
     * <ul>
     *     <li>将请求参数转换为实体对象。</li>
     *     <li>生成唯一ID（Snowflake算法）。</li>
     *     <li>设置当前登录会员ID。</li>
     *     <li>记录创建和更新时间。</li>
     *     <li>执行数据库插入操作。</li>
     * </ul>
     *
     * @param req 包含乘客信息的保存请求
     */
    @Override
    public void save(PassengerSaveReq req) {
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);
        passenger.setId(SnowUtil.getSnowflakeNextId());
        passenger.setMemberId(LoginMemberContext.getId());
        DateTime now = DateTime.now();
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);
        passengerMapper.insert(passenger);
    }

    /**
     * 查询指定会员ID下的乘客列表。
     * <ul>
     *     <li>构建查询条件，根据会员ID筛选。</li>
     *     <li>执行查询并转换结果为响应DTO列表。</li>
     * </ul>
     *
     * @param req 查询请求，包含会员ID
     * @return 乘客列表响应DTO
     */
    @Override
    public List<PassengerListResp> queryList(PassengerListReq req) {
        List<Passenger> passengers = passengerMapper.selectList(new QueryWrapper<Passenger>().eq("memberId", req.getMemberId()));
        return BeanUtil.copyToList(passengers, PassengerListResp.class);
    }

    /**
     * 根据ID删除乘客记录。
     *
     * @param req 包含乘客ID的删除请求
     */
    @Override
    public void delete(PassengerDelReq req) {
        passengerMapper.delete(new QueryWrapper<Passenger>().eq("id", req.getId()));
    }

    /**
     * 查询当前登录会员的乘客信息列表，并按名称降序排列。
     *
     * @return 当前会员的乘客信息响应DTO列表
     */
    @Override
    public List<PassengerQueryResp> queryMine() {
        List<Passenger> passengers = passengerMapper.selectList(new QueryWrapper<Passenger>()
                .eq("memberId", LoginMemberContext.getId())
                .orderByDesc("name"));
        return BeanUtil.copyToList(passengers, PassengerQueryResp.class);
    }
}
