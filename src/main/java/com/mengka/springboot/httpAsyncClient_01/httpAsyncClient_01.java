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
import java.util.concurrent.Future;

/**
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_01 {

    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_01 start..");


        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            // Execute request
            log.info("--------，start CloseableHttpAsyncClient response1..");
            final HttpGet request1 = new HttpGet("http://www.apache.org/");
            Future<HttpResponse> future = httpclient.execute(request1, null);
            // and wait until a response is received
            HttpResponse response1 = future.get();
            System.out.println("--------，1 = "+request1.getRequestLine() + "->" + response1.getStatusLine());

            // One most likely would want to use a callback for operation result
            log.info("--------，start CloseableHttpAsyncClient response2..");
            final CountDownLatch latch1 = new CountDownLatch(1);
            final HttpGet request2 = new HttpGet("http://www.apache.org/");
            httpclient.execute(request2, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response2) {
                    latch1.countDown();
                    System.out.println("--------，2 = "+request2.getRequestLine() + "->" + response2.getStatusLine());

                    //get http response body
                    try {
                        HttpEntity httpEntity = response2.getEntity();
                        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
                        System.out.println("response3 body = "+responseString);
                    } catch (Exception e) {
                        System.out.println("completed error!"+e.getMessage());
                    }
                }

                public void failed(final Exception ex) {
                    latch1.countDown();
                    System.out.println(request2.getRequestLine() + "->" + ex);
                }

                public void cancelled() {
                    latch1.countDown();
                    System.out.println(request2.getRequestLine() + " cancelled");
                }

            });
            latch1.await();

            // In real world one most likely would also want to stream
            // request and response body content
            log.info("--------，start CloseableHttpAsyncClient response2..");
            final CountDownLatch latch2 = new CountDownLatch(1);
            final HttpGet request3 = new HttpGet("http://www.apache.org/");
            HttpAsyncRequestProducer producer3 = HttpAsyncMethods.create(request3);
            AsyncCharConsumer<HttpResponse> consumer3 = new AsyncCharConsumer<HttpResponse>() {

                HttpResponse response;

                @Override
                protected void onResponseReceived(final HttpResponse response) {
                    this.response = response;
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
                    System.out.println(request2.getRequestLine() + "->" + response3.getStatusLine());


                    //get http response body
                    try {
                        HttpEntity httpEntity = response3.getEntity();
                        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
                        System.out.println("response3 body = "+responseString);
                    } catch (Exception e) {
                        System.out.println("completed error!"+e.getMessage());
                    }
                }

                public void failed(final Exception ex) {
                    latch2.countDown();
                    System.out.println(request2.getRequestLine() + "->" + ex);
                }

                public void cancelled() {
                    latch2.countDown();
                    System.out.println(request2.getRequestLine() + " cancelled");
                }

            });
            latch2.await();

        } finally {
            httpclient.close();
        }

        log.info("httpAsyncClient_01 end..");
    }
}
