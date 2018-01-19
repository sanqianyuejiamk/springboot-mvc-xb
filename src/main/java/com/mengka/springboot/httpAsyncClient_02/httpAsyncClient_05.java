package com.mengka.springboot.httpAsyncClient_02;

import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * http连接池调优：
 * 1. 连接数，通过设立全局最大连接数和单route连接数，增加吞吐能力。用户可通过HttpClientBuilder#maxConnTotal和#maxConnPerRoute分别设置；
 * 2. 获取连接的超时时间，调小超时时间能够有效提高响应速度并且降低积压请求量，但相应的也会增加请求失败的几率。用户可以通过RequestConfig的connectionRequestTimeout进行设置；
 * 3. 建立连接和route响应的超时时间，调小能够有效的降低bad request对连接的占用，留给质量更好的请求，有效提高系统提高吞吐能力及响应速度。否则有可能在峰值期被慢请求占满连接池，导致系统瘫痪。两者分别可通过RequestConfig#connectionTimeout和socketTimeout进行设置；
 * 4. 开启BackoffStrategyExec，对状况差的route进行降级处理，将连接让给其他route；
 *
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_05 {

    public static void main(String[] args)throws Exception{
        log.info("httpAsyncClient_05 start..");

        String getUrl = "http://127.0.0.1:8089/v1/kv/a1";
        String content1 = HttpClientUtil.get(getUrl);
        log.info("HttpClientUtil get content = {}",content1);

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("t","11");
        params.put("name","啊啊");
        String content2 = HttpClientUtil.get(getUrl,params);
        log.info("HttpClientUtil post content2 = {}",content2);

//        String content3 = HttpClientUtil.post(getUrl,params);
//        log.info("HttpClientUtil post content3 = {}",content3);

        log.info("httpAsyncClient_05 end..");
    }
}
