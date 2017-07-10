package com.mengka.springboot.hystrix_04;

import com.mengka.springboot.util.TimeUtil;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixThreadPoolKey;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 *  网络IO请求情况
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class CommandWithFallbackViaNetwork extends HystrixCommand<String> {

    private final int id;

    public CommandWithFallbackViaNetwork(int id) {
        super(Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(Constant.REMOTE_SERVICE_GROUP))
                .andCommandKey(HystrixCommandKey.Factory.asKey("RemoteServiceCmd"))
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServicePool")));
        this.id = id;
    }

    @Override
    protected String run() throws Exception {
        log.info("运行时异常触发降级");
        throw new RuntimeException("运行时异常触发降级");
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
        return new FallbackViaNetwork(id).execute();
    }

    /**
     *  降级处理逻辑
     *  1）使用不同的线程池做隔离，防止上层线程池泡满，影响降级逻辑；
     *
     */
    private static class FallbackViaNetwork extends HystrixCommand<String> {

        private final int id;

        public FallbackViaNetwork(int id) {
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey(Constant.REMOTE_SERVICE_GROUP))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("RemoteServiceFallbackCmd"))
                    .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("RemoteServiceFallbackPool")));
            this.id = id;
        }

        @Override
        protected String run() throws Exception {
            String result = "降级处理逻辑触发[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS) + "，id=" + id;
            log.info("FallbackViaNetwork execute result = "+result);
            return result;
        }

        @Override
        protected String getFallback() {
            return null;
        }
    }
}
