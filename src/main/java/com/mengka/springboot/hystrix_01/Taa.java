package com.mengka.springboot.hystrix_01;

import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class Taa {

    public static void main(String[] args) throws Exception {
        /**同步调用*/
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("Synchronous-hystrix");
        //使用execute()同步调用代码,效果等同于:helloWorldCommand.queue().get();
        String result = helloWorldCommand.execute();
        log.info("Synchronous-hystrix result = " + result);

        /**异步调用*/
        helloWorldCommand = new HelloWorldCommand("Asynchronous-hystrix");
        //异步调用,可自由控制获取结果时机,
        Future<String> future = helloWorldCommand.queue();
        //get操作不能超过command定义的超时时间,默认:1秒
        result = future.get(100, TimeUnit.MILLISECONDS);
        log.info("Asynchronous-hystrix result = " + result);
    }
}
