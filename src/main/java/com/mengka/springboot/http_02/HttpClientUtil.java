package com.mengka.springboot.http_02;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.apache.http.*;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.CharArrayBuffer;
import org.apache.http.util.EntityUtils;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * HTTP连接池
 * 1）在 keep-alive 时间内，可以使用同一个 tcp 连接发起多次 http 请求;
 * 2）如果不使用连接池，在大并发的情况下，每次连接都会打开一个端口，使系统资源很快耗尽，无法建立新的连接;
 * <p>
 * <p>
 * 》》httpClient 维护着两个 Set，leased(被占用的连接集合) 和 avaliabled(可用的连接集合) 两个集合，
 * 释放连接就是将被占用连接放到可用连接里面；
 * <p>
 * HTTP1.1 默认启用 Keep-Alive
 * 在一次连接里面，可以发起多个 http 请求。如果没有 Keep-Alive，每次 http 请求都要发起一次 tcp 连接。
 * <p>
 * <p>
 * 》》keep-alive设置：(Response Headers)
 * 1）Connection:Keep-Alive
 * 2）Keep-Alive:timeout=5,max=100
 * <p>
 * Connection:Keep-Alive,设置http连接为长连接；
 * timeout=5,表示在5s之内如果没有发起新的http请求，服务器将断开这次tcp连接；（如果发起新的请求，断开连接时间将继续刷新为 5s）
 * max=100,表示在这次的tcp连接之内，最多允许发送100次http请求，100次之后，即使在timeouts时间之内发起的请求，服务器依然会断开这次tcp连接；
 * <p>
 * <p>
 * 》》超时时间设置：
 * connectTimeout 请求超时时间；
 * socketTimeout 等待数据超时时间；
 * connectionRequestTimeout 连接不够用时等待超时时间，一定要设置，如果不设置的话，如果连接池连接不够用，就会线程阻塞；
 * <p>
 * RequestConfig requestConfig = RequestConfig.custom()
 * .setConnectTimeout(3 * 1000)    // 请求超时时间
 * .setSocketTimeout(60 * 1000)    // 等待数据超时时间
 * .setConnectionRequestTimeout(500)  // 连接超时时间
 * .build();
 * <p>
 * <p>
 * 》》连接池设置：
 * int maxTotal = 200;// 将最大连接数增加
 * int maxPerRoute = 40;// 将每个路由基础的连接增加
 * int maxRoute = 100; // 将目标主机的最大连接数增加
 * <p>
 * httpClient = createHttpClient(maxTotal, maxPerRoute, maxRoute, hostname, port);
 * <p>
 * maxTotal，整个连接池最大连接数 根据自己的场景决定；
 * maxRoute，路由个数，是根据连接到的主机对MaxTotal的一个细分；
 * maxPerRoute，每个路由的最大连接数（如果只有一个路由，可以让他等于最大值）；
 * <p>
 * 》路由是对maxTotal的细分；
 * <p>
 * 》》情况一：
 * 如果没有释放连接，导致连接池内连接不够用，并且因为我没有设置连接不够用等待超时时间(connectionRequestTimeout)，一直等待导致线程阻塞。
 *
 * @author mengka
 * @date 2017/06/15.
 */
public class HttpClientUtil {

    private static final int CONNECT_TIMEOUT = 3 * 1000;//请求超时时间
    private static final int SOCKET_TIMEOUT = 60 * 1000;//等待数据超时时间
    private static final int CONNECT_REQUEST_TIMEOUT = 500;//连接超时时间

    private static final int maxTotal = 200;// 将最大连接数增加
    private static final int maxPerRoute = 40;// 将每个路由基础的连接增加
    private static final int maxRoute = 100; // 将目标主机的最大连接数增加

    private static CloseableHttpClient httpClient = null;

    private final static Object syncLock = new Object();

    private static final String DEFAULT_ENCODING = "utf-8";

    private static void config(HttpRequestBase httpRequestBase, Map<String, String> headerMap) {
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)//连接超时时间
                .setConnectTimeout(CONNECT_TIMEOUT)//请求超时时间
                .setSocketTimeout(SOCKET_TIMEOUT)//等待数据超时时间
                .build();
        httpRequestBase.setConfig(requestConfig);
        //请求头信息
        if (headerMap != null) {
            for (String key : headerMap.keySet()) {
                httpRequestBase.addHeader(key, headerMap.get(key));
            }
        }
    }

    /**
     * 获取HttpClient对象
     *
     * @return
     * @create 2015年12月18日
     */
    public static CloseableHttpClient getHttpClient(String url) {
        String hostname = url.split("/")[2];
        int port = 80;
        if (hostname.contains(":")) {
            String[] arr = hostname.split(":");
            hostname = arr[0];
            port = Integer.parseInt(arr[1]);
        }
        if (httpClient == null) {
            synchronized (syncLock) {
                if (httpClient == null) {
                    httpClient = createHttpClient(maxTotal, maxPerRoute, maxRoute, hostname, port);
                }
            }
        }
        return httpClient;
    }

    /**
     * 创建HttpClient对象
     *
     * @return
     * @create 2015年12月18日
     */
    public static CloseableHttpClient createHttpClient(int maxTotal,
                                                       int maxPerRoute, int maxRoute, String hostname, int port) {
        ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
        LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
        Registry<ConnectionSocketFactory> registry = RegistryBuilder
                .<ConnectionSocketFactory>create()
                .register("http", plainsf)
                .register("https", sslsf).build();
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
        // 将最大连接数增加
        cm.setMaxTotal(maxTotal);
        // 将每个路由基础的连接增加
        cm.setDefaultMaxPerRoute(maxPerRoute);
        HttpHost httpHost = new HttpHost(hostname, port);
        // 将目标主机的最大连接数增加
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



        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .setRetryHandler(httpRequestRetryHandler).build();

        final IdleConnectionMonitorThread staleMonitor = new IdleConnectionMonitorThread(cm);
        staleMonitor.start();

        return httpClient;
    }

    private static void setPostParams(HttpPost httpost, Map<String, Object> params) {
        if (params == null) {
            return;
        }
        String content = JSON.toJSONString(params);
        httpost.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
    }

    private static String setGetParams(String url, Map<String, Object> params) {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }
        try {
            UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(nvps, "UTF-8");
            String queryString = getContent(encodedFormEntity, "UTF-8");
            if (StringUtils.isNotBlank(queryString)) {
                url = url + "?" + queryString;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String getContent(HttpEntity entity, String charset) throws Exception {
        int i = (int) entity.getContentLength();
        if (i < 0) {
            i = 4096;
        }
        final Reader reader = new InputStreamReader(entity.getContent(), charset);
        final CharArrayBuffer buffer = new CharArrayBuffer(i);
        final char[] tmp = new char[1024];
        int l;
        while ((l = reader.read(tmp)) != -1) {
            buffer.append(tmp, 0, l);
        }
        return buffer.toString();
    }

    /**
     * POST请求URL获取内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static CloseableHttpResponse post(String url, Map<String, String> headerMap, Map<String, Object> params) throws IOException {
        HttpPost httppost = new HttpPost(url);
        config(httppost, headerMap);

        setPostParams(httppost, params);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    public static CloseableHttpResponse post(String url, Map<String, String> headerMap, String content) throws IOException {
        HttpPost httppost = new HttpPost(url);
        config(httppost, headerMap);

        httppost.setEntity(new StringEntity(content, ContentType.APPLICATION_JSON));
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    public static CloseableHttpResponse doPostFormUrlEncodedGetStatusLine(String url, Map<String, String> headerMap, Map<String, Object> params) throws IOException {
        HttpPost httppost = new HttpPost(url);
        config(httppost, headerMap);

        setPostFormUrlEncodedParams(httppost, params);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httppost,
                    HttpClientContext.create());
            return response;
        } catch (Exception e) {
            throw e;
        }
    }

    private static void setPostFormUrlEncodedParams(HttpEntityEnclosingRequestBase httpost,
                                                    Map<String, Object> params) throws UnsupportedEncodingException {
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            nvps.add(new BasicNameValuePair(key, params.get(key).toString()));
        }

        httpost.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));
    }

    public static String getHttpResponseBody(HttpResponse response)
            throws UnsupportedOperationException, IOException {
        if (response == null) {
            return null;
        }
        String body = null;
        HttpEntity entity = response.getEntity();
        if (entity != null && entity.isStreaming()) {
            String encoding = entity.getContentEncoding() != null ? entity.getContentEncoding().getValue() : null;
            body = inputStream2String(entity.getContent(), encoding);
        }
        return body;
    }

    public static String get(String url, Map<String, String> headerMap, Map<String, Object> params) {
        url = setGetParams(url, params);
        return get(url, headerMap);
    }

    /**
     * GET请求URL获取内容
     *
     * @param url
     * @return
     * @create 2015年12月18日
     */
    public static String get(String url, Map<String, String> headerMap) {
        HttpGet httpget = new HttpGet(url);
        config(httpget, headerMap);
        CloseableHttpResponse response = null;
        try {
            response = getHttpClient(url).execute(httpget,
                    HttpClientContext.create());
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static String inputStream2String(InputStream in, String charsetName) {
        if (in == null) {
            return null;
        }

        InputStreamReader inReader = null;

        try {
            if (StringUtils.isBlank(charsetName)) {
                inReader = new InputStreamReader(in, DEFAULT_ENCODING);
            } else {
                inReader = new InputStreamReader(in, charsetName);
            }

            int readLen = 0;
            char[] buffer = new char[1024];
            StringBuffer strBuf = new StringBuffer();
            while ((readLen = inReader.read(buffer)) != -1) {
                strBuf.append(buffer, 0, readLen);
            }

            return strBuf.toString();
        } catch (IOException e) {
            System.out.println(e);
        } finally {
            closeStream(inReader);
        }

        return null;
    }

    private static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    /**
     * 后台监控线程检查连接
     * 监控线程会定时的去检查连接池中连接是否还"新鲜"，如果过期了，或者空闲时间超过了设置的最大时间阀值，就将其从连接池删除。
     * <p>
     * closeExpiredConnections()
     * 该方法关闭超过连接保持时间的连接，并从池中移除。
     * <p>
     * closeIdleConnections(timeout,tunit)
     * 该方法关闭空闲时间超过timeout的连接，空闲时间从交还给连接池时开始，不管是否已过期，超过空闲时间则关闭。
     * <p>
     * 【官方文档】
     * http://hc.apache.org/httpcomponents-client-4.2.x/tutorial/html/connmgmt.html#d5e652
     */
    public static class IdleConnectionMonitorThread extends Thread {

        private final HttpClientConnectionManager connMgr;
        private volatile boolean shutdown;

        public IdleConnectionMonitorThread(HttpClientConnectionManager connMgr) {
            super();
            this.connMgr = connMgr;
        }

        @Override
        public void run() {
            try {
                while (!shutdown) {
                    synchronized (this) {
                        wait(1000);
                        // Close expired connections
                        connMgr.closeExpiredConnections();
                        // Optionally, close connections
                        // that have been idle longer than 30 sec
                        connMgr.closeIdleConnections(30, TimeUnit.SECONDS);
                    }
                }
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() {
            shutdown = true;
            synchronized (this) {
                notifyAll();
            }
        }
    }

}