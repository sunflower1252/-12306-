package com.sanzuniao.util;

import cn.hutool.core.util.IdUtil;

import static cn.hutool.core.util.IdUtil.getSnowflake;

/**
 * 封装hutool雪花算法
 * @author yangguang
 */
public class SnowUtil {
    private static long dataCenterId = 1; // 数据中心
    private static long workerId = 1; //机器标识

    public static long getSnowflakeNextId() {
        return getSnowflake(workerId, dataCenterId).nextId();
    }

    public static String getSnowflakeNextIdStr() {
        return IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
    }
}
