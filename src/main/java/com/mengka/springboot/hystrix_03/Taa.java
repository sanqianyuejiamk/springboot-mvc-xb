package com.mengka.springboot.hystrix_03;

import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class Taa {

    public static void main(String[] args) {
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand command1 = new RequestCacheCommand(2);
            RequestCacheCommand command2 = new RequestCacheCommand(2);
            String result1 = command1.execute();
            boolean isResponseFromCache1 = command1.isResponseFromCache();
            log.info("command1 execute result1 = " + result1 + " , isResponseFromCache1 = " + isResponseFromCache1);

            String result2 = command2.execute();
            boolean isResponseFromCache2 = command2.isResponseFromCache();
            log.info("command2 execute result2 = " + result2 + " , isResponseFromCache2 = " + isResponseFromCache2);

        } finally {
            context.shutdown();
        }

        context = HystrixRequestContext.initializeContext();
        try {
            RequestCacheCommand command3 = new RequestCacheCommand(2);
            String result3 = command3.execute();
            boolean isResponseFromCache3 = command3.isResponseFromCache();
            log.info("command3 execute result3 = " + result3 + " , isResponseFromCache3 = " + isResponseFromCache3);
        } finally {
            context.shutdown();
        }
    }
}
