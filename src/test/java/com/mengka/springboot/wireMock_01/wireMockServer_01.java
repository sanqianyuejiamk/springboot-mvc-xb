package com.mengka.springboot.wireMock_01;

import com.alibaba.fastjson.JSON;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.mengka.springboot.model.MengkaApplyRsp;
import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.fluent.Content;
import org.apache.http.client.fluent.Request;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import java.util.Date;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 *  WireMock,连线模拟工具（虚拟服务）
 * 1）使用WireMock可以极大减少创建连线测试桩所需要的时间；
 * 2）在JUnit里使用 WireMock可以更容易地编写各种测试用例；
 * 3）在自动化测试中，可以用于创建远程虚拟服务的接口，这样就可以在CI（持续集成）环境里构建系统；（CI比如：TeamCity或Jenkins）
 *
 * @author mengka
 * @date 2017/06/13.
 */
@Slf4j
public class wireMockServer_01 {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    /**
     *  虚拟服务
     *  端口：8089
     *  地址：匹配"/v1/kv/.*"
     *
     *  访问：
     *  http://127.0.0.1:8089/v1/kv/a1
     *
     *  结果：
     *  {"message":"a wireMock result[2017-06-13 22:04:24","result":true}
     *
     * @throws Exception
     */
    @Test
    public void start_api_server()throws Exception{
        log.info("start_api_server start..");

        //启动虚拟服务
        startApiServer();

        Thread.sleep(6000000);

        log.info("start_api_server end..");
    }

    public void startApiServer(){
        String wireMockResult = getApiServerResult();

        stubFor(get(urlMatching("/v1/kv/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(wireMockResult)));
    }

    public String getApiServerResult(){
        MengkaApplyRsp mengkaApplyRsp = new MengkaApplyRsp();
        mengkaApplyRsp.setResult(true);
        mengkaApplyRsp.setMessage("a wireMock result["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        return JSON.toJSONString(mengkaApplyRsp);
    }

    /**
     * 使用httpcomponents-client验证虚拟服务：
     *  https://hc.apache.org/httpcomponents-client-ga/quickstart.html
     *
     * @throws Exception
     */
    @Test
    public void verify_api_result_apache_httpcomponents()throws Exception{
        String url = "http://127.0.0.1:8089/v1/kv/a1";

        //启动虚拟服务
        startApiServer();

        //验证api
        Content content = Request.Get(url)
                .execute()
                .returnContent();

        MengkaApplyRsp mengkaApplyRsp = JSON.parseObject(content.toString(),MengkaApplyRsp.class);
        log.info("-----mengkaApplyRsp----- message = {}",mengkaApplyRsp.getMessage());

        Thread.sleep(100000);
    }

    /**
     * 使用springweb httpclient验证虚拟服务：
     *   http://127.0.0.1:8089/v1/kv/a1/hyy044101331
     *
     * @throws Exception
     */
    @Test
    public void verify_api_result_springweb_httpclient()throws Exception{
        String url = "http://127.0.0.1:8089/v1/kv/a1/{id}";
        String idUriVariables = "hyy044101331";

        //启动虚拟服务
        startApiServer();

        //验证api
        ResponseEntity<MengkaApplyRsp> response = new RestTemplate()
                .getForEntity(url, MengkaApplyRsp.class, idUriVariables);
        MengkaApplyRsp mengkaApplyRsp = response.getBody();
        log.info("-----mengkaApplyRsp----- message = {}",mengkaApplyRsp.getMessage());

        Thread.sleep(100000);
    }
}
