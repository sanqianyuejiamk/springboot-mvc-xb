package com.mengka.springboot.wireMock_01;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.util.EntityUtils;
import org.junit.Rule;
import org.junit.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 *  启动一个服务端返回503的接口服务：
 *  curl -v http://127.0.0.1:8089/v1/kv/a5
 *
 * @author mengka
 * @date 2017/06/16.
 */
@Slf4j
public class wireMockServer_500 {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void start_api_server()throws Exception{
        log.info("start_api_server start..");

        String url = "http://127.0.0.1:8089/v1/kv/a5";

        //启动虚拟服务
        startApiServer();

        //验证api
        HttpResponse response = Request.Get(url)
                .execute()
                .returnResponse();
        log.info("[completed] " + response.getStatusLine());
        final int statusCode = response.getStatusLine().getStatusCode();
        log.info("[completed] statusCode is {}",statusCode);

        //get http response body
        HttpEntity httpEntity = response.getEntity();
        String responseString = EntityUtils.toString(httpEntity, "UTF-8");
        log.info("response body = {}", responseString);

        Thread.sleep(600000);

        log.info("start_api_server end..");
    }

    public void startApiServer(){
        stubFor(get(urlEqualTo("/v1/kv/a5"))
                .willReturn(aResponse()
                        .withStatus(503)));
    }
}
