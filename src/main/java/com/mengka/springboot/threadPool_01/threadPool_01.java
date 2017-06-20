package com.mengka.springboot.threadPool_01;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author mengka
 * @date 2017/06/20.
 */
@Slf4j
public class threadPool_01 {

    public static void main(String[] args) throws Exception {
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

        /**
         *  delay 3s，然后执行；
         *
         */
        Runnable task = () -> log.info("Scheduling: " + TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        ScheduledFuture<?> future = executor.schedule(task, 3, TimeUnit.SECONDS);
        log.info("executor schedule task.[{}",TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));

//        TimeUnit.MILLISECONDS.sleep(7337);
        TimeUnit.MILLISECONDS.sleep(1337);

        /**
         *  remainingDelay = 3s - 7337ms = -4376ms
         *  remainingDelay = 3s - 1337ms = 1632ms
         */
        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        log.info("Remaining Delay: {}ms", remainingDelay);

        executor.shutdown();
    }
}
