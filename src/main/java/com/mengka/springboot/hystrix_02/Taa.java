package com.mengka.springboot.hystrix_02;

import lombok.extern.slf4j.Slf4j;

/**
 *  fallback降级
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class Taa {

    public static void main(String[] args){
        HelloWorldCommand helloWorldCommand = new HelloWorldCommand("test-Fallback");
        String result = helloWorldCommand.execute();
        log.info("test-Fallback result = " + result);
    }
}
