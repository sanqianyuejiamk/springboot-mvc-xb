package com.mengka.springboot.token_bucket_01;

import com.google.common.util.concurrent.RateLimiter;
import com.mengka.springboot.util.TimeUtil;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.stream.IntStream;

/**
 *  RateLimiter控制使用速率
 *
 * @author mengka
 * @version 2020/11/28
 * @since
 */
public class Tbb {

    public static void main(String... args){
        // given
        RateLimiter rateLimiter = RateLimiter.create(100);

        // when
        long start = System.currentTimeMillis();
        IntStream.range(0, 1000).forEach(i -> {
            rateLimiter.acquire();
            doSomeLimitedOperation();
        });
        long endTime = System.currentTimeMillis();
        System.out.println("endTime - start = " + (endTime - start)+"ms");
    }

    public static void doSomeLimitedOperation(){
        System.out.println("[Just for test.." + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
    }
}
