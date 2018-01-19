package com.mengka.springboot.httpAsyncClient_https_03;

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
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * 》》http连接池：
 * 建立一次http连接三次握手开销很大，http连接池使用已经建立好的http连接，这样花费就很小，吞吐率更大；
 * <p>
 * 【官方文档】http://hc.apache.org/httpcomponents-client-4.3.x/tutorial/html/connmgmt.html
 * <p>
 * 》》为什么要用Http连接池：
 * 1）降低延迟：如果不采用连接池，每次连接发起Http请求的时候都会重新建立TCP连接(经历3次握手)，用完就会关闭连接(4次挥手)，
 * 如果采用连接池则减少了这部分时间损耗，别小看这几次握手，本人经过测试发现，基本上3倍的时间延迟；
 * 2）支持更大的并发：如果不采用连接池，每次连接都会打开一个端口，
 * 在大并发的情况下系统的端口资源很快就会被用完，导致无法建立新的连接；
 * <p>
 * 》》连接池中连接都是在发起请求的时候建立，并且都是长连接；
 * <p>
 * 》》连接池释放连接的时候，并不会直接对TCP连接的状态有任何改变，只是维护了两个Set，leased和avaliabled，leased代表被占用的连接集合，avaliabled代表可用的连接的集合，释放连接的时候仅仅是将连接从leased中remove掉了，并把连接放到avaliabled集合中；
 *
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class httpAsyncClient_07 {

    private static final int defaultTimeout = 3000;

    private static final int maxTotal = 200;// 将最大连接数增加
    private static final int maxPerRoute = 40;// 将每个路由基础的连接增加
    private static final int maxRoute = 100; // 将目标主机的最大连接数增加

    /**
     * keystore用于存放自己的密钥和公钥
     */
    public static final String clientKeyStoreFile = "/Users/hyy044101331/work_springcloud/work_sanqianyuejia/sanqianyuejia/innotek-tutorial/src/main/java/com/mengka/springboot/SSLSocke_02/mykey.keystore";
    public static final String clientKeyStorePwd = "123456";

    /**
     * truststore用于存放所有需要信任方的公钥
     */
    public static final String clientTrustKeyStoreFile = "/Users/hyy044101331/java_tools/tomcat-iotapp_sz1.3/conf/keys/mykeytrust.keystore";
    public static final String clientTrustKeyStorePwd = "654321";


    public static void main(String[] args) throws Exception {
        log.info("httpAsyncClient_07 start..");

        //连接地址,hostname,port
        String url = "https://127.0.0.1:8073/cabbage-iotapp/api/v1/devices?pageNo=1&pageSize=10";
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
            final HttpGet request1 = new HttpGet("https://127.0.0.1:8073/cabbage-iotapp/api/v1/devices?pageNo=1&pageSize=10");
            request1.setHeader(HttpHeaders.USER_AGENT, "mengka test agent");
            request1.setConfig(requestConfig);


            SSLContext sslContext = getSSLContext();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext,
                    NoopHostnameVerifier.INSTANCE);

            /**step02*/
            ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//            LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
            Registry<ConnectionSocketFactory> registry = RegistryBuilder
                    .<ConnectionSocketFactory>create()
                    .register("http", plainsf)
                    .register("https", sslsf)
                    .build();
            HttpHost httpHost = new HttpHost(hostname, port);


            // http连接管理
            // 当使用了请求连接池管理器（比如PoolingClientConnectionManager）后，HttpClient就可以同时执行多个线程的请求了
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
            /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
            httpclient = HttpClients.custom()
                    .setConnectionManager(cm)
//                    .setSSLSocketFactory(sslsf)
                    .setRetryHandler(httpRequestRetryHandler).build();


            CloseableHttpResponse response = httpclient.execute(request1, HttpClientContext.create());
            log.info(request1.getRequestLine() + "->" + response.getStatusLine());

            //get http response body
            HttpEntity httpEntity = response.getEntity();
            String responseString = EntityUtils.toString(httpEntity, "UTF-8");
            log.info("response body = {}", responseString);

            //释放连接
            response.close();
        } finally {
            //关闭连接
            httpclient.close();
        }

        log.info("httpAsyncClient_06 end..");
    }

    /**
     * 使用我们的密钥存储密钥管理器和信任管理器
     *
     * @return
     */
    public static SSLContext getSSLContext() {
        String keyPass = clientKeyStorePwd;
        String trustPass = clientTrustKeyStorePwd;
        String keyStorePath = clientKeyStoreFile;
        String trustStorePath = clientTrustKeyStoreFile;

        char[] keyPassphrase = keyPass.toCharArray();
        char[] trustPassphrase = trustPass.toCharArray();
        SSLContext c = null;
        try {
            //通过PKCS12的证书格式得到密钥对象
            KeyStore ks = KeyStore.getInstance("JKS");

            //keycert.p12包含客户端的证书和key
            ks.load(new FileInputStream(keyStorePath), keyPassphrase);
            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(ks, keyPassphrase);
            KeyStore tks = KeyStore.getInstance("JKS");
            //用CA为服务器提供证书，若想连接服务器则将他添加到密钥库中
            tks.load(new FileInputStream(trustStorePath), trustPassphrase);
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(tks);
            c = SSLContext.getInstance("TLSv1.2");
            c.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);//如果信任证书设置为null 则从jdk证书库去取
        } catch (KeyStoreException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (CertificateException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        } catch (KeyManagementException e) {
            e.printStackTrace();
            System.out.println(e.toString());
        }
        return c;
    }

}
