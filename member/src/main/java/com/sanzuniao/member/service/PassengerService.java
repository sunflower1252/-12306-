package com.sanzuniao.member.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sanzuniao.member.domain.Passenger;
import com.sanzuniao.member.req.PassengerDelReq;
import com.sanzuniao.member.req.PassengerListReq;
import com.sanzuniao.member.req.PassengerSaveReq;
import com.sanzuniao.member.resp.PassengerListResp;
import com.sanzuniao.member.resp.PassengerQueryResp;

import java.util.List;

/**
 * @author yangguang
 * @description 针对表【passenger(乘车人)】的数据库操作Service
 * @createDate 2024-06-28 20:50:53
 */
public interface PassengerService extends IService<Passenger> {

    void save(PassengerSaveReq req);

    List<PassengerListResp> queryList(PassengerListReq req);

    void delete(PassengerDelReq req);

    List<PassengerQueryResp> queryMine();
}
