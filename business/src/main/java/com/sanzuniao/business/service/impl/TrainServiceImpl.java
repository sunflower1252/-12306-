package com.sanzuniao.business.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sanzuniao.business.domain.Train;
import com.sanzuniao.business.mapper.TrainMapper;
import com.sanzuniao.business.service.TrainService;
import org.springframework.stereotype.Service;

/**
 * @author yangguang
 * @description 针对表【train(车次)】的数据库操作Service实现
 * @createDate 2024-06-29 17:38:12
 */
@Service
public class TrainServiceImpl extends ServiceImpl<TrainMapper, Train>
        implements TrainService {

}




