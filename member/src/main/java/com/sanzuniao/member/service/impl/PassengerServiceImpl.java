package com.sanzuniao.member.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.context.LoginMemberContext;
import com.sanzuniao.member.domain.Passenger;
import com.sanzuniao.member.mapper.PassengerMapper;
import com.sanzuniao.member.req.PassengerListReq;
import com.sanzuniao.member.req.PassengerSaveReq;
import com.sanzuniao.member.resp.PassengerListResp;
import com.sanzuniao.member.service.PassengerService;
import com.sanzuniao.util.SnowUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangguang
 * @description 针对表【passenger(乘车人)】的数据库操作Service实现
 * @createDate 2024-06-28 20:50:53
 */
@Service
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger>
        implements PassengerService {

    @Resource
    private PassengerMapper passengerMapper;


    /**
     * 保存乘客信息。
     * <p>
     * 该方法接收一个乘客保存请求（PassengerSaveReq），将其转化为Passenger实体对象，
     * 并在实体对象中自动填充创建时间和更新时间为当前时间，分配一个新的成员ID（使用雪花算法生成），
     * 随后将此实体对象持久化存储到数据库中。
     *
     * @param req 乘客保存请求对象，其中包含了待保存的乘客详细信息。
     */
    @Override
    public void save(PassengerSaveReq req) {
        // 利用BeanUtil工具将请求参数转换为Passenger实体，以便进行数据库操作
        Passenger passenger = BeanUtil.copyProperties(req, Passenger.class);

        // 为乘客分配一个唯一的成员ID
        passenger.setId(SnowUtil.getSnowflakeNextId());
        // 从线程中取出将token解析后的数据，取出会员id
        long memberId = LoginMemberContext.getId();
        passenger.setMemberId(memberId);

        // 设置创建时间和更新时间为当前时间
        DateTime now = DateTime.now();
        passenger.setCreateTime(now);
        passenger.setUpdateTime(now);

        // 调用数据访问层（Mapper）的insert方法，将乘客信息保存至数据库
        passengerMapper.insert(passenger);
    }


    @Override
    public List<PassengerListResp> quertList(PassengerListReq req) {
        Long memberId = req.getMemberId();
        List<Passenger> passengerList = passengerMapper.selectList(new QueryWrapper<Passenger>()
                .eq("memberId", memberId));
        List<PassengerListResp> passengerListResp = BeanUtil.copyToList(passengerList, PassengerListResp.class);
        return passengerListResp;
    }

}




