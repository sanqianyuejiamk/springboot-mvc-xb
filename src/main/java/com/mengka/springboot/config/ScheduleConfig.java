package com.mengka.springboot.config;

import com.mengka.springboot.manager.LimitInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author huangyy
 * @description
 * @date 2017/05/02.
 */
@Slf4j
@Component
@Configurable
@EnableScheduling
public class ScheduleConfig {

    @Autowired
    private LimitInfoManager limitInfoManager;

    @Scheduled(cron = "${job.credit.manager.cron}")
    public void updateCreditAmount() {
        log.info("定时更新用户额度信息:{}",new Date());
        try{
            limitInfoManager.printLimitInfo();
        }catch (Exception e){
            log.error("定时更新用户额度信息异常:{}",e);
        }
        log.info("定时更新用户额度信息任务执行结束");
    }

}
