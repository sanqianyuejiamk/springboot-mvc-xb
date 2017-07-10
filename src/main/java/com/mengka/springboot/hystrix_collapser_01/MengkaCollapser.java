package com.mengka.springboot.hystrix_collapser_01;

import com.netflix.hystrix.*;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 命令调用合并HystrixCollapser
 * 1，多个请求合并到一个线程/信号下批量执行；
 * 2，多个相同业务的请求合并到一个连接中执行；
 * 3，调用合并可以降低线程交互次数和IO次数；
 *
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class MengkaCollapser extends HystrixCollapser<List<String>, String, Integer> {

    private final Integer key;

    public MengkaCollapser(Integer key) {
        this.key = key;
    }

    @Override
    public Integer getRequestArgument() {
        return key;
    }

    @Override
    protected HystrixCommand<List<String>> createCommand(Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        return new BatchCommand(collapsedRequests);
    }

    @Override
    protected void mapResponseToRequests(List<String> batchResponse, Collection<CollapsedRequest<String, Integer>> collapsedRequests) {
        int count = 0;
        for (CollapsedRequest<String, Integer> request : collapsedRequests) {
            //手动匹配请求和响应
            String tmpRsp = batchResponse.get(count++);
            log.info("mapResponseToRequests setResponse: "+tmpRsp);
            request.setResponse(tmpRsp);
        }
    }

    /**
     *  合并请求处理的命令模式
     *
     */
    private static final class BatchCommand extends HystrixCommand<List<String>> {

        private final Collection<CollapsedRequest<String, Integer>> requests;

        public BatchCommand(Collection<CollapsedRequest<String, Integer>> requests) {
            super(Setter
                    .withGroupKey(HystrixCommandGroupKey.Factory.asKey("BatchGroup"))
                    .andCommandKey(HystrixCommandKey.Factory.asKey("BatchCmd")));
            this.requests = requests;
        }

        @Override
        protected List<String> run() throws Exception {
            log.info("---------, BatchCommand run requests size = "+requests.size());
            ArrayList<String> response = new ArrayList<String>();
            for (CollapsedRequest<String, Integer> request : requests) {
                log.info("-------, response add "+request.getArgument());
                response.add("ValueForKey: " + request.getArgument());
            }
            return response;
        }
    }
}
