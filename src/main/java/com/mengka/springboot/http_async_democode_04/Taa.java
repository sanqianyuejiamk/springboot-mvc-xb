package com.mengka.springboot.http_async_democode_04;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 *  异步线程池工具类
 *
 * 样例代码：
 * https://www.programcreek.com/java-api-examples/index.php?source_dir=mozu-java-master/mozu-javaasync-sdk/src/main/java/com/mozu/api/utils/MozuHttpClientPool.java#
 *
 * 参考：
 * https://www.programcreek.com/java-api-examples/index.php?class=org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager&method=setDefaultMaxPerRoute
 *
 * @author huangyy
 * @date 2018/02/05.
 */
@Slf4j
public class Taa {

    public static void main(String[] args) throws Exception {
        log.info("异步线程池HttpAsyncClient test start..");
        String url = "http://google.com";
        HttpRequestBase httpMethod = new HttpGet(url);

        CloseableHttpAsyncClient httpAsyncClient = MozuHttpClientPool.getInstance().getHttpClient();
        httpAsyncClient.execute(httpMethod, new MengkaCallback());

        log.info("异步线程池HttpAsyncClient test end..");
    }

    public static class MengkaCallback implements FutureCallback<HttpResponse> {

        @Override
        public void completed(HttpResponse response) {
            log.info("--------, " + response.getStatusLine().getStatusCode());
            log.info("--------, " + getHttpContent(response));

            HttpClientUtils.closeQuietly(response);
        }

        @Override
        public void failed(Exception ex) {
            log.info("--------, MengkaCallback failed..");
        }

        @Override
        public void cancelled() {
            log.info("--------, MengkaCallback cancelled..");
        }
    }

    public static String getHttpContent(HttpResponse response) {
        HttpEntity entity = response.getEntity();
        String body = null;
        if (entity == null) {
            return null;
        }
        try {

            body = EntityUtils.toString(entity, "utf-8");

        } catch (ParseException e) {
            log.warn("the response's content inputstream is corrupt", e);
        } catch (IOException e) {
            log.warn("the response's content inputstream is corrupt", e);
        }
        return body;
    }
}
