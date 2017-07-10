package com.mengka.springboot.hystrix_03;

import com.mengka.springboot.util.TimeUtil;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;

/**
 *  请求缓存：
 *  在(CommandKey/CommandGroup)相同的情况下,直接共享结果，降低依赖调用次数，在高并发和CacheKey碰撞率高场景下可以提升性能；
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class RequestCacheCommand extends HystrixCommand<String> {

    private final int id;

    public RequestCacheCommand( int id) {
        super(HystrixCommandGroupKey.Factory.asKey("RequestCacheGroup"));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        log.info("mainThread = " + Thread.currentThread().getName());
        String result = " thread:{" + Thread.currentThread().getName()+"}["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS)+ " execute id=" + id;
        return result;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
}
