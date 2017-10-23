package com.mengka.springboot.httpAsyncClient_01;

import com.mengka.springboot.httpAsyncClient_02.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Future;

/**
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_02 {

    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_02 start..");

        test_get_content1();

//        test_get_content2();

        log.info("httpAsyncClient_02 end..");
    }

    public static void test_get_content1() throws Exception {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            // Execute request
            final HttpGet request1 = new HttpGet("http://127.0.0.1:8089/v1/kv/a1");
            Future<HttpResponse> future = httpclient.execute(request1, null);
            // and wait until a response is received
            HttpResponse response1 = future.get();
            log.info(request1.getRequestLine() + "->" + response1.getStatusLine());

            //get http response body
            HttpEntity httpEntity = response1.getEntity();
            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
            log.info("response body = {}", responseString);
        } finally {
            //关闭连接
            httpclient.close();
        }
    }

    public static void test_get_content2() throws Exception {
        CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
        try {
            // Start the client
            httpclient.start();

            // Execute request
            final HttpGet request1 = new HttpGet("http://127.0.0.1:8089/v1/kv/a1");
            Future<HttpResponse> future = httpclient.execute(request1, null);
            // and wait until a response is received
            HttpResponse response1 = future.get();
            log.info(request1.getRequestLine() + "->" + response1.getStatusLine());

            //get http response body
            HttpEntity httpEntity = response1.getEntity();
            String responseString = HttpClientUtil.getContent(httpEntity, "UTF-8");
            log.info("response body = {}", responseString);
        } finally {
            httpclient.close();
        }
    }


}
