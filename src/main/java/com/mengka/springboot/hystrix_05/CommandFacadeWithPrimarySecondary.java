package com.mengka.springboot.hystrix_05;

import com.netflix.config.DynamicBooleanProperty;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class CommandFacadeWithPrimarySecondary extends HystrixCommand<String> {

    private final static DynamicBooleanProperty usePrimary = DynamicPropertyFactory.getInstance().getBooleanProperty("primarySecondary.usePrimary", true);

    private final int id;

    /**
     * 配置信号量隔离方式
     *
     * @param id
     */
    public CommandFacadeWithPrimarySecondary(int id) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(Constant.MENGKA_GROUP))
                .andCommandKey(HystrixCommandKey.Factory.asKey("MengkaCmd"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        if (usePrimary.get()) {
            return new PrimaryCommand(id).execute();
        } else {
            return new SecondaryCommand(id).execute();
        }
    }

    @Override
    protected String getFallback() {
        return "static-fallback-" + id;
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }


    private static class PrimaryCommand extends HystrixCommand<String> {

        private final int id;

        /**
         * 配置依赖超时时间,600毫秒
         *
         * @param id
         */
        public PrimaryCommand(int id) {
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(Constant.MENGKA_GROUP))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("PrimaryCmd"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("PrimaryPool"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(600)));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
            log.info("-----PrimaryCommand-----");
            return "responseFromPrimary-" + id;
        }

        @Override
        protected String getFallback() {
            log.info("-----PrimaryCommand getFallback-----");
            return "PrimaryCommand-fallback-" + id;
        }
    }


    private static class SecondaryCommand extends HystrixCommand<String> {

        private final int id;

        /**
         * 配置依赖超时时间,100毫秒
         *
         * @param id
         */
        public SecondaryCommand(int id) {
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(Constant.MENGKA_GROUP))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("SecondaryCmd"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("SecondaryPool"))
                    .andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(100)));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
            log.info("-----SecondaryCommand-----");
            return "responseFromSecondary-" + id;
        }

        @Override
        protected String getFallback() {
            log.info("-----SecondaryCommand getFallback-----");
            return "SecondaryCommand-fallback-" + id;
        }
    }
}
