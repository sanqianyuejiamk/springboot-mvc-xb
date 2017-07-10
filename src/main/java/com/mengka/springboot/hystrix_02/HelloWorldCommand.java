package com.mengka.springboot.hystrix_02;

import com.mengka.springboot.util.TimeUtil;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class HelloWorldCommand extends HystrixCommand<String> {

    private final String name;

    /**
     *  配置依赖超时时间,500毫秒
     *
     * @param name
     */
    public HelloWorldCommand(String name) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("HelloWorldGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(500)));
        this.name = name;
    }

    /**
     * Hystrix回退触发条件
     * 1）程序抛出非HystrixBadRequestException异常；
     * 2）程序运行超时；
     * 3）熔断启动；
     * 4）线程池已满；
     * 5）限流；
     *
     * @return
     */
    @Override
    protected String getFallback() {
        return "exeucute Falled";
    }

    /**
     *  调用超时1s，触发降级getFallback()和断路器逻辑
     *
     * @return
     * @throws Exception
     */
    @Override
    protected String run() throws Exception {
        //接口超时:超过500ms没有响应
        Thread.sleep(1000);
        log.info("mainThread = " + Thread.currentThread().getName());
        String result = "Hello " + name +" thread{" + Thread.currentThread().getName()+"}["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        return result;
    }
}
