package com.mengka.springboot.http_02;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * （默认重试3次）
 * 由于国内连接不上google网站，所以请求在重试5次之后抛出了NoHttpResponseException异常
 * <p>
 * 》》连接超时时间：
 * setSocketTimeout(60 * 1000)
 *
 * 02:08:43.896 [main] DEBUG org.apache.http.impl.conn.DefaultManagedHttpClientConnection - http-outgoing-4: set socket timeout to 60000
 * <p>
 * 02:08:47.905 [main] INFO com.mengka.springboot.http_02.Taa - --------, result = null
 * org.apache.http.NoHttpResponseException: google.com:80 failed to respond
 * at org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:141)
 * at org.apache.http.impl.conn.DefaultHttpResponseParser.parseHead(DefaultHttpResponseParser.java:56)
 * at org.apache.http.impl.io.AbstractMessageParser.parse(AbstractMessageParser.java:259)
 * at org.apache.http.impl.DefaultBHttpClientConnection.receiveResponseHeader(DefaultBHttpClientConnection.java:163)
 * at org.apache.http.impl.conn.CPoolProxy.receiveResponseHeader(CPoolProxy.java:165)
 * at org.apache.http.protocol.HttpRequestExecutor.doReceiveResponse(HttpRequestExecutor.java:273)
 * at org.apache.http.protocol.HttpRequestExecutor.execute(HttpRequestExecutor.java:125)
 * at org.apache.http.impl.execchain.MainClientExec.execute(MainClientExec.java:272)
 * at org.apache.http.impl.execchain.ProtocolExec.execute(ProtocolExec.java:185)
 * at org.apache.http.impl.execchain.RetryExec.execute(RetryExec.java:89)
 * at org.apache.http.impl.execchain.RedirectExec.execute(RedirectExec.java:111)
 * at org.apache.http.impl.client.InternalHttpClient.doExecute(InternalHttpClient.java:185)
 * at org.apache.http.impl.client.CloseableHttpClient.execute(CloseableHttpClient.java:83)
 * at com.mengka.springboot.http_02.HttpClientUtil.get(HttpClientUtil.java:356)
 * at com.mengka.springboot.http_02.Taa.main(Taa.java:15)
 *
 * 【重试5次】
 * 02:08:31.867 [main] DEBUG org.apache.http.impl.execchain.RetryExec - The target server failed to respond
 * 02:08:35.879 [main] DEBUG org.apache.http.impl.execchain.RetryExec - The target server failed to respond
 * 02:08:39.887 [main] DEBUG org.apache.http.impl.execchain.RetryExec - The target server failed to respond
 * 02:08:43.895 [main] DEBUG org.apache.http.impl.execchain.RetryExec - The target server failed to respond
 * 02:08:47.905 [main] INFO com.mengka.springboot.http_02.Taa - --------, result = null
 * org.apache.http.NoHttpResponseException: google.com:80 failed to respond
 *
 * @author huangyy
 * @date 2018/02/05.
 */
public class Taa {

    private static final Log log = LogFactory.getLog(Taa.class);

    public static void main(String[] args) {
        String result = HttpClientUtil.get("http://google.com", null);
        log.info("--------, result = " + result);
    }
}
