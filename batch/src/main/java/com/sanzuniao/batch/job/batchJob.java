package com.sanzuniao.batch.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 只是适合单体项目，不适合分布式系统
 * 构建快，适合快速需要的场景
 * 没有办法随时更改配置
 */
@Component
@EnableScheduling
public class batchJob {

    @Scheduled(cron = "0/5 * * * * *")
    public void test() {
        // 创建分布式锁，解决集群问题
        System.out.println("springBoot Job TEST");
    }
}
