package com.mengka.springboot.httpAsyncClient_01;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_async_04 {

    private static final int defaultTimeout = 3000;

    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_async_03 start..");

        String url = "http://127.0.0.1:8089/v1/kv/a1";

        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            /**step01*/
            // In real world one most likely would also want to stream
            // request and response body content
            final CountDownLatch latch2 = new CountDownLatch(1);

            /**
             * connectionRequestTimeout：获取连接的超时时间；
             * connectionTimeout：建立连接的超时时间；
             * socketTimeout：建立route响应的超时时间；
             *
             * 调小超时时间能够有效提高响应速度并且降低积压请求量，但相应的也会增加请求失败的几率;
             * 调小能够有效的降低bad request对连接的占用，留给质量更好的请求，有效提高系统提高吞吐能力及响应速度。否则有可能在峰值期被慢请求占满连接池，导致系统瘫痪;
             */
            final RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(defaultTimeout)
                    .setConnectTimeout(defaultTimeout)
                    .setConnectionRequestTimeout(defaultTimeout)
                    .build();
            final HttpGet request3 = new HttpGet(url);
            request3.setHeader(HttpHeaders.USER_AGENT, "mengka test agent");
            request3.setConfig(requestConfig);

            HttpAsyncRequestProducer producer3 = HttpAsyncMethods.create(request3);

            /**step02*/
            //http请求处理
            AsyncCharConsumer<HttpResponse> consumer3 = new AsyncCharConsumer<HttpResponse>() {

                HttpResponse response;

                @Override
                protected void onResponseReceived(final HttpResponse response) {
                    this.response = response;
                }

                @Override
                protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
                    // Do something useful

                    String content = buf.toString();
                    log.info("-----body----- onCharReceived content = {}",content);
                    return;
                }

                @Override
                protected void releaseResources() {
                }

                @Override
                protected HttpResponse buildResult(final HttpContext context) {
                    return this.response;
                }

            };


            /**step03*/
            httpclient.execute(producer3, consumer3, new FutureCallback<HttpResponse>() {

                public void completed(final HttpResponse response3) {
                    latch2.countDown();
                    log.info("[completed] " + request3.getRequestLine() + "->" + response3.getStatusLine());

                    final int statusCode = response3.getStatusLine().getStatusCode();
                    log.info("[completed] statusCode is {}",statusCode);
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
