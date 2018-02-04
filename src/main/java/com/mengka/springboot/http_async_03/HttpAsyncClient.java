package com.mengka.springboot.http_async_03;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.Consts;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthSchemeProvider;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.MalformedChallengeException;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.Lookup;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.auth.*;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.CodingErrorAction;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 异步的HTTP请求对象，可设置代理
 */
@Slf4j
public class HttpAsyncClient {

    private static int socketTimeout = 60 * 1000;// 设置等待数据超时时间60秒 根据业务调整

    private static int connectTimeout = 3 * 1000;// 连接超时

    private static int connectRequestTimeout = 500;//连接超时时间

    private static int poolSize = 3000;// 连接池最大连接数

    private static int maxPerRoute = 1500;// 每个主机的并发最多只有1500

    // http代理相关参数
    private String host = "";
    private int port = 0;
    private String username = "";
    private String password = "";

    // 异步httpclient
    private CloseableHttpAsyncClient asyncHttpClient;

    // 异步加代理的httpclient
    private CloseableHttpAsyncClient proxyAsyncHttpClient;

    public HttpAsyncClient() {
        try {
            this.asyncHttpClient = createAsyncClient(false);
            this.proxyAsyncHttpClient = createAsyncClient(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CloseableHttpAsyncClient createAsyncClient(boolean proxy)
            throws KeyManagementException, UnrecoverableKeyException,
            NoSuchAlgorithmException, KeyStoreException,
            MalformedChallengeException, IOReactorException {

        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setSocketTimeout(socketTimeout)
                .setConnectionRequestTimeout(connectRequestTimeout)
                .build();

        SSLContext sslcontext = SSLContexts.createDefault();

        UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(
                username, password);

        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy>create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(sslcontext))
                .build();

        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .setSoKeepAlive(true)
                .build();
        // 设置连接池大小
        ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(ioReactor, null, sessionStrategyRegistry, null);
        conMgr.setMaxTotal(poolSize);
        conMgr.setDefaultMaxPerRoute(maxPerRoute);


        ConnectionConfig connectionConfig = ConnectionConfig.custom()
                .setMalformedInputAction(CodingErrorAction.IGNORE)
                .setUnmappableInputAction(CodingErrorAction.IGNORE)
                .setCharset(Consts.UTF_8).build();

        Lookup<AuthSchemeProvider> authSchemeRegistry = RegistryBuilder
                .<AuthSchemeProvider>create()
                .register(AuthSchemes.BASIC, new BasicSchemeFactory())
                .register(AuthSchemes.DIGEST, new DigestSchemeFactory())
                .register(AuthSchemes.NTLM, new NTLMSchemeFactory())
                .register(AuthSchemes.SPNEGO, new SPNegoSchemeFactory())
                .register(AuthSchemes.KERBEROS, new KerberosSchemeFactory())
                .build();
        conMgr.setDefaultConnectionConfig(connectionConfig);

        //检测连接线程
        IdleConnectionMonitorThread monitor = new IdleConnectionMonitorThread(conMgr);
        monitor.setDaemon(true);
        monitor.start();

        if (proxy) {
            return HttpAsyncClients.custom()
                    .setConnectionManager(conMgr)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setProxy(new HttpHost(host, port))
                    .setDefaultCookieStore(new BasicCookieStore())
                    .setDefaultRequestConfig(requestConfig).build();
        } else {
            return HttpAsyncClients.custom()
                    .setConnectionManager(conMgr)
                    .setDefaultCredentialsProvider(credentialsProvider)
                    .setDefaultAuthSchemeRegistry(authSchemeRegistry)
                    .setDefaultCookieStore(new BasicCookieStore())
                    .setDefaultRequestConfig(requestConfig).build();
        }

    }

    public CloseableHttpAsyncClient getAsyncHttpClient() {
        return asyncHttpClient;
    }

    public CloseableHttpAsyncClient getProxyAsyncHttpClient() {
        return proxyAsyncHttpClient;
    }


    // Watches for stale connections and evicts them.
    private class IdleConnectionMonitorThread extends Thread {
        // The manager to watch.
        private final PoolingNHttpClientConnectionManager cm;
        // Use a BlockingQueue to stop everything.
        private final BlockingQueue<Stop> stopSignal = new ArrayBlockingQueue<Stop>(1);

        // Pushed up the queue.
        private class Stop {
            // The return queue.
            private final BlockingQueue<Stop> stop = new ArrayBlockingQueue<Stop>(1);

            // Called by the process that is being told to stop.
            public void stopped() {
                // Push me back up the queue to indicate we are now stopped.
                stop.add(this);
            }

            // Called by the process requesting the stop.
            public void waitForStopped() throws InterruptedException {
                // Wait until the callee acknowledges that it has stopped.
                stop.poll(30, TimeUnit.SECONDS);
            }

        }

        IdleConnectionMonitorThread(PoolingNHttpClientConnectionManager cm) {
            super();
            this.cm = cm;
        }

        @Override
        public void run() {
            try {
                // Holds the stop request that stopped the process.
                Stop stopRequest;
                // Every 5 seconds.
                while ((stopRequest = stopSignal.poll(5, TimeUnit.SECONDS)) == null) {
                    // Close expired connections
                    cm.closeExpiredConnections();
                    // Optionally, close connections that have been idle too long.
                    cm.closeIdleConnections(60, TimeUnit.SECONDS);
                    // Look at pool stats.
                    log.debug("Stats: {}", cm.getTotalStats());
                }
                // Acknowledge the stop request.
                stopRequest.stopped();
            } catch (InterruptedException ex) {
                // terminate
            }
        }

        public void shutdown() throws InterruptedException {
            log.trace("Shutting down client pool");
            // Signal the stop to the thread.
            Stop stop = new Stop();
            stopSignal.add(stop);
            // Wait for the stop to complete.
            stop.waitForStopped();
            // Close the pool - Added
            try {
                asyncHttpClient.close();
            } catch (IOException ioe) {
                log.info("IO Exception while closing HttpClient connecntions.");
            }
            // Close the connection manager.
            synchronized (this) {
                notifyAll();
            }
            log.trace("Client pool shut down");
        }
    }
}