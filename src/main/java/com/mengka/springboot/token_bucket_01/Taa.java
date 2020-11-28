package com.mengka.springboot.token_bucket_01;

import com.google.common.util.concurrent.RateLimiter;
import com.mengka.springboot.util.TimeUtil;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 *  >>RateLimiter使用：
 *  处理一个任务列表，但不希望每秒的任务提交超过两个。
 *
 *  RateLimiter经常用于限制对一些物理资源或者逻辑资源的访问速率。与Semaphore 相比，Semaphore 限制了并发访问的数量而不是使用速率。
 *
 *
 *  >>结果：
 * [Just for test..2020-11-28 09:16:25
 * [Just for test..2020-11-28 09:16:25
 * [Just for test..2020-11-28 09:16:26
 * [Just for test..2020-11-28 09:16:26
 * [Just for test..2020-11-28 09:16:27
 * [Just for test..2020-11-28 09:16:27
 * [Just for test..2020-11-28 09:16:28
 * [Just for test..2020-11-28 09:16:28
 * [Just for test..2020-11-28 09:16:29
 * [Just for test..2020-11-28 09:16:29
 * [Just for test..2020-11-28 09:16:30
 * [Just for test..2020-11-28 09:16:30
 *
 * >>文档：
 * http://ifeve.com/guava-ratelimiter/
 *
 * @author mengka
 * @version 2020/11/28
 * @since
 */
public class Taa {

    private static final BlockingQueue<Runnable> queue = new LinkedBlockingDeque<Runnable>(100);//有界队列

    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 600, 30,
            TimeUnit.SECONDS, queue, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String... args) {
        //速率是每秒两个许可
        final RateLimiter rateLimiter = RateLimiter.create(2.0);

        while (true) {
            rateLimiter.acquire(); // 也许需要等待
            executor.execute(new TaaTask());
        }


    }

    static class TaaTask implements Runnable {

        @Override
        public void run() {
            System.out.println("[Just for test.." + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        }
    }


}
