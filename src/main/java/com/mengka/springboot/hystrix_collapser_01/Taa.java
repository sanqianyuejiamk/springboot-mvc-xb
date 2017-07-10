package com.mengka.springboot.hystrix_collapser_01;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixEventType;
import com.netflix.hystrix.HystrixRequestLog;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import lombok.extern.slf4j.Slf4j;
import java.util.concurrent.Future;

/**
 *  命令调用合并的使用
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class Taa {

    public static void main(String[] args)throws Exception{
        HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            Future<String> f1 = new MengkaCollapser(1).queue();
            Future<String> f2 = new MengkaCollapser(2).queue();
            Future<String> f3 = new MengkaCollapser(3).queue();
            Future<String> f4 = new MengkaCollapser(4).queue();
            log.info("ValueForKey: 1 is "+ f1.get());
            log.info("ValueForKey: 2 is "+ f2.get());
            log.info("ValueForKey: 3 is "+ f3.get());
            log.info("ValueForKey: 4 is "+ f4.get());

            int size = HystrixRequestLog.getCurrentRequest().getExecutedCommands().size();
            log.info("CurrentRequest getExecutedCommands size = "+size);

            HystrixCommand<?> command = HystrixRequestLog.getCurrentRequest().getExecutedCommands().toArray(new HystrixCommand<?>[1])[0];
            log.info("GetValueForKey is "+ command.getCommandKey().name());
            log.info("contain HystrixEventType.COLLAPSED: "+command.getExecutionEvents().contains(HystrixEventType.COLLAPSED));
            log.info("contain HystrixEventType.SUCCESS: "+command.getExecutionEvents().contains(HystrixEventType.SUCCESS));
        } finally {
            context.shutdown();
        }
    }
}
