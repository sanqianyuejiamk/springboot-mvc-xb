package com.mengka.springboot.httpAsyncClient_01;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.CountDownLatch;

/**
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_async_04 {

    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_async_03 start..");

        String url = "http://127.0.0.1:8089/v1/kv/a1";

        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            // In real world one most likely would also want to stream
            // request and response body content
            final CountDownLatch latch2 = new CountDownLatch(1);
            final HttpGet request3 = new HttpGet(url);
            HttpAsyncRequestProducer producer3 = HttpAsyncMethods.create(request3);

            AsyncCharConsumer<HttpResponse> consumer3 = new AsyncCharConsumer<HttpResponse>() {

                HttpResponse response;

                @Override
                protected void onResponseReceived(final HttpResponse response) {
                    this.response = response;

                    //get http response body
                    try {
                        HttpEntity httpEntity = response.getEntity();
                        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
                        log.info("response3 body = {}", responseString);
                    } catch (Exception e) {
                        log.error("completed error!", e);
                    }
                }

                @Override
                protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
                    // Do something useful
                }

                @Override
                protected void releaseResources() {
                }

                @Override
                protected HttpResponse buildResult(final HttpContext context) {
                    return this.response;
                }

            };


            httpclient.execute(producer3, consumer3, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response3) {
                    latch2.countDown();
                    log.info("[completed] " + request3.getRequestLine() + "->" + response3.getStatusLine());

                    //get http response body
                    try {
                        HttpEntity httpEntity = response3.getEntity();
                        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
                        log.info("response3 body = {}", responseString);
                    } catch (Exception e) {
                        log.error("completed error!", e);
                    }
                }

                public void failed(final Exception ex) {
                    latch2.countDown();
                    log.info("[failed] " + request3.getRequestLine() + "->" + ex);
                }

                public void cancelled() {
                    latch2.countDown();
                    log.info("[cancelled] " + request3.getRequestLine() + " cancelled");
                }

            });
            log.info("httpclient execute url[{}]", url);

            latch2.await();

        } finally {
            httpclient.close();
        }
        log.info("httpAsyncClient_async_03 start..");
    }
}
