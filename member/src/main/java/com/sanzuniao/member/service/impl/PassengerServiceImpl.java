package com.sanzuniao.member.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.member.domain.Passenger;
import com.sanzuniao.member.mapper.PassengerMapper;
import com.sanzuniao.member.service.PassengerService;
import org.springframework.stereotype.Service;

/**
 * @author yangguang
 * @description 针对表【passenger(乘车人)】的数据库操作Service实现
 * @createDate 2024-06-28 20:50:53
 */
@Service
public class PassengerServiceImpl extends ServiceImpl<PassengerMapper, Passenger>
        implements PassengerService {

}




