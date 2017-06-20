package com.mengka.springboot.threadPool_02;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author mengka
 * @date 2017/06/20.
 */
@Slf4j
public class threadPool_03 {

    public static void main(String[] args) {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        Runnable task = () -> {
            try {
                TimeUnit.SECONDS.sleep(2);
                System.out.println("Scheduling: " + TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
            } catch (InterruptedException e) {
                System.err.println("task interrupted");
            }
        };

        /**
         *  在上一个任务执行完成后1s时间，在执行下一个任务；
         */
        executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.SECONDS);
    }
}
