package com.mengka.springboot.hystrix_01;

import com.mengka.springboot.util.TimeUtil;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 *  利用命令模式封装依赖逻辑
 *  1）Hystrix使用命令模式HystrixCommand包装依赖调用逻辑，每个命令在单线程中/信号授权下执行；
 *  2）可配置依赖调用超时时间，当调用超时时，直接返回或执行fallback逻辑；
 *  3）Hystrix回退触发条件；{1-5}
 *  4）提供熔断器组件；
 *  5）提供近实时依赖的统计和监控；
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class HelloWorldCommand extends HystrixCommand<String> {

    private final String name;

    /**
     * GroupKey: 指定命令组名
     * CommandKey: 每个CommandKey代表一个依赖抽象,相同的依赖要使用相同的CommandKey名称;
     * ThreadPoolKey: 指定线程池名称
     *
     * 【注】：
     *  依赖隔离的根本就是对相同CommandKey的依赖做隔离；
     *
     * @param name
     */
    public HelloWorldCommand(String name) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                .andCommandKey(HystrixCommandKey.Factory.asKey("HelloWorldCmd"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("HelloWorldPool")));
        this.name = name;
    }

    @Override
    protected String run() throws Exception {
        log.info("mainThread = " + Thread.currentThread().getName());
        String result = "Hello " + name +" thread:" + Thread.currentThread().getName()+"["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        return result;
    }
}
