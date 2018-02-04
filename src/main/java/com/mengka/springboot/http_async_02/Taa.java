package com.mengka.springboot.http_async_02;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *  POST请求
 *
 * @author huangyy
 * @date 2018/02/05.
 */
@Slf4j
public class Taa {

    public static void main(String[] args) throws Exception {
        log.info("异步线程池HttpAsyncClient test start..");


        String url = "http://127.0.0.1:8089/v1/kv/a1";
        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>();
        urlParams.add(new BasicNameValuePair("appid", "2"));


        exeAsyncReq(url, urlParams, null, new MengkaCallback());

        log.info("异步线程池HttpAsyncClient test end..");
    }

    protected static void exeAsyncReq(String baseUrl, List<BasicNameValuePair> urlParams,
                               List<BasicNameValuePair> postBody, FutureCallback callback) throws Exception {
        HttpAsyncClient httpAsyncClient = new HttpAsyncClient();
        CloseableHttpAsyncClient hc = httpAsyncClient.getAsyncHttpClient();
        hc.start();

        HttpClientContext localContext = HttpClientContext.create();
        BasicCookieStore cookieStore = new BasicCookieStore();

        HttpRequestBase httpMethod = new HttpPost(baseUrl);

        if (null != postBody) {
            log.debug("--------,exeAsyncReq post postBody={}", postBody);
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postBody, "UTF-8");
            ((HttpPost) httpMethod).setEntity(entity);
        }

        if (null != urlParams) {
            String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(urlParams));
            httpMethod.setURI(new URI(httpMethod.getURI().toString() + "?" + getUrl));
        }

        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        //执行请求
        hc.execute(httpMethod, localContext, callback);
        log.info("--------, httpAsyncClient execute post request..");
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
