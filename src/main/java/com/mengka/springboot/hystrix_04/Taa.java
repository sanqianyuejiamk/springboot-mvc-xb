package com.mengka.springboot.hystrix_04;

import lombok.extern.slf4j.Slf4j;

/**
 *  fallback降级逻辑命令嵌套
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class Taa {

    public static void main(String[] args){
        CommandWithFallbackViaNetwork commandWithFallbackViaNetwork = new CommandWithFallbackViaNetwork(14001);
        String result = commandWithFallbackViaNetwork.execute();
        log.info("commandWithFallbackViaNetwork execute result = "+result);
    }
}
