package com.mengka.springboot.threadPool_02;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *  超时回调线程池Executors.newScheduledThreadPool():
 *      executor.scheduleAtFixedRate(),每3s执行一次task任务;
 *      executor.scheduleWithFixedDelay(),在上一个任务执行完成后1s时间，在执行下一个任务;
 *
 * @author mengka
 * @date 2017/06/20.
 */
@Slf4j
public class threadPool_02 {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> log.info("Scheduling: " + TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));

        /**
         *  每3s执行一次task任务
         */
        int initialDelay = 0;
        int period = 3;
        executor.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
    }
}
