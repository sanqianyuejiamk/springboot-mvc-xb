package com.mengka.springboot.hystrix_05;

import com.netflix.config.ConfigurationManager;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class Taa {

    public static void main(String[] args){
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            //设置usePrimary值
            ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", true);
//            ConfigurationManager.getConfigInstance().setProperty("primarySecondary.usePrimary", false);

            CommandFacadeWithPrimarySecondary commandFacadeWithPrimarySecondary = new CommandFacadeWithPrimarySecondary(14001);
            String result = commandFacadeWithPrimarySecondary.execute();
            log.info("commandFacadeWithPrimarySecondary result = "+result);
        }finally {
            context.shutdown();
            ConfigurationManager.getConfigInstance().clear();
        }
    }


}
