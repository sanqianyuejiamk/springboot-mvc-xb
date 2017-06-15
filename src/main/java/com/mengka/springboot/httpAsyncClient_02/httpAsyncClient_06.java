package com.mengka.springboot.httpAsyncClient_02;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 *  》》http连接池：
 *  建立一次http连接三次握手开销很大，http连接池使用已经建立好的http连接，这样花费就很小，吞吐率更大；
 *
 *  》》为什么要用Http连接池：
 *  1）降低延迟：如果不采用连接池，每次连接发起Http请求的时候都会重新建立TCP连接(经历3次握手)，用完就会关闭连接(4次挥手)，
 *  如果采用连接池则减少了这部分时间损耗，别小看这几次握手，本人经过测试发现，基本上3倍的时间延迟；
 *  2）支持更大的并发：如果不采用连接池，每次连接都会打开一个端口，
 *  在大并发的情况下系统的端口资源很快就会被用完，导致无法建立新的连接；
 *
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_06 {

    private static final int defaultTimeout = 3000;

    private static final int maxTotal = 200;// 将最大连接数增加
    private static final int maxPerRoute = 40;// 将每个路由基础的连接增加
    private static final int maxRoute = 100; // 将目标主机的最大连接数增加

    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_06 start..");

        //连接地址,hostname,port
        String url = "http://127.0.0.1:8089/v1/kv/a1";
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }

        CloseableHttpClient httpclient = null;
        try {

            /**step01*/
            // Execute request
            final RequestConfig requestConfig = RequestConfig.copy(RequestConfig.DEFAULT)
                    .setSocketTimeout(defaultTimeout)
                    .setConnectTimeout(defaultTimeout)
                    .setConnectionRequestTimeout(defaultTimeout)
                    .build();
            final HttpGet request1 = new HttpGet("http://127.0.0.1:8089/v1/kv/a1");
            request1.setHeader(HttpHeaders.USER_AGENT, "mengka test agent");
            request1.setConfig(requestConfig);

            /**step02*/
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", plainsf)
                    .register("https", sslsf)
                    .build();
            HttpHost httpHost = new HttpHost(hostname, port);
            // http连接管理
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(maxTotal);
            cm.setDefaultMaxPerRoute(maxPerRoute);
            cm.setMaxPerRoute(new HttpRoute(httpHost), maxRoute);

            // 请求重试处理
            HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
                public boolean retryRequest(IOException exception,
                                            int executionCount, HttpContext context) {
                    if (executionCount >= 5) {// 如果已经重试了5次，就放弃
                        return false;
                    }
                    if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                        return true;
                    }
                    if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                        return false;
                    }
                    if (exception instanceof InterruptedIOException) {// 超时
                        return false;
                    }
                    if (exception instanceof UnknownHostException) {// 目标服务器不可达
                        return false;
                    }
                    if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                        return false;
                    }
                    if (exception instanceof SSLException) {// SSL握手异常
                        return false;
                    }

                    HttpClientContext clientContext = HttpClientContext
                            .adapt(context);
                    HttpRequest request = clientContext.getRequest();
                    // 如果请求是幂等的，就再次尝试
                    if (!(request instanceof HttpEntityEnclosingRequest)) {
                        return true;
                    }
                    return false;
                }
            };

            /**step03*/
            httpclient = HttpClients.custom()
                    .setConnectionManager(cm)
                    .setRetryHandler(httpRequestRetryHandler).build();


            CloseableHttpResponse response = httpclient.execute(request1, HttpClientContext.create());
            log.info(request1.getRequestLine() + "->" + response.getStatusLine());

            //get http response body
            HttpEntity httpEntity = response.getEntity();
            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
            log.info("response body = {}", responseString);
        } finally {
            httpclient.close();
        }

        log.info("httpAsyncClient_06 end..");
    }
}
