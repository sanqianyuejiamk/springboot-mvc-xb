package com.mengka.springboot.httpAsyncClient_01;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.EntityUtils;
import java.util.concurrent.CountDownLatch;

/**
 * 异步获取http content
 *
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_async_03 {

    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_async_03 start..");

        String url = "http://127.0.0.1:8089/v1/kv/a1";

        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            // One most likely would want to use a callback for operation result
            final CountDownLatch latch1 = new CountDownLatch(1);
            final HttpGet request2 = new HttpGet(url);
            httpclient.execute(request2, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response2) {
                    latch1.countDown();
                    log.info("[completed] " + request2.getRequestLine() + "->" + response2.getStatusLine());

                    //get http response body
                    try {
                        HttpEntity httpEntity = response2.getEntity();
                        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
                        log.info("response body = " + responseString);
                    } catch (Exception e) {
                        log.info("completed error!", e);
                    }
                }

                public void failed(final Exception ex) {
                    latch1.countDown();
                    log.info("[failed] " + request2.getRequestLine() + "->" + ex);
                }

                public void cancelled() {
                    latch1.countDown();
                    log.info("[cancelled] " + request2.getRequestLine() + " cancelled");
                }

            });
            log.info("httpclient execute url[{}]", url);

            latch1.await();

        } finally {
            httpclient.close();
        }
        log.info("httpAsyncClient_async_03 start..");
    }
}
