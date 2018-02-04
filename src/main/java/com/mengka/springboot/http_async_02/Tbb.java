package com.mengka.springboot.http_async_02;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 *  GET请求
 *  请求地址goolge.com，无法访问的网站；
 *
 * @author huangyy
 * @date 2018/02/05.
 */
@Slf4j
public class Tbb {

    public static void main(String[] args) throws Exception {
        log.info("异步线程池HttpAsyncClient test start..");

        String url = "http://google.com";
        List<BasicNameValuePair> urlParams = new ArrayList<BasicNameValuePair>();
        urlParams.add(new BasicNameValuePair("appid", "2"));

        exeAsyncReq_get(url, urlParams, null, new Taa.MengkaCallback());

        log.info("异步线程池HttpAsyncClient test end..");
    }

    protected static void exeAsyncReq_get(String baseUrl, List<BasicNameValuePair> urlParams,
                                          List<BasicNameValuePair> postBody, FutureCallback callback) throws Exception {
        HttpAsyncClient httpAsyncClient = new HttpAsyncClient();
        CloseableHttpAsyncClient hc = httpAsyncClient.getAsyncHttpClient();
        hc.start();

        HttpClientContext localContext = HttpClientContext.create();
        BasicCookieStore cookieStore = new BasicCookieStore();

        HttpRequestBase httpMethod = new HttpGet(baseUrl);

        if (null != urlParams) {
            String getUrl = EntityUtils.toString(new UrlEncodedFormEntity(urlParams));
            httpMethod.setURI(new URI(httpMethod.getURI().toString() + "?" + getUrl));
        }

        localContext.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);
        //执行请求
        hc.execute(httpMethod, localContext, callback);
        log.info("--------, httpAsyncClient execute post request..");
    }
}
