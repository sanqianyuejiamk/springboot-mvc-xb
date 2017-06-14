package com.mengka.springboot.wireMock_02;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * @author mengka
 * @date 2017/06/14.
 */
@Slf4j
public class wireMockServer_02 {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);//未指定端口时，默认8080

    /**
     *  虚拟服务
     *  端口：8089
     *  地址：匹配"/v1/kv/.*"
     *
     *  访问：
     *  http://127.0.0.1:8089/v2/kv/a1
     *
     *  结果：
     *  get_kv_response.json
     *
     * @throws Exception
     */
    @Test
    public void start_api_server()throws Exception{
        log.info("start_api_server start..");

        Path responsePath = Paths.get(Thread.currentThread().getContextClassLoader().
                getResource("get_kv_response.json").toURI());
        byte[] resultData = Files.readAllBytes(responsePath);

        stubFor(get(urlMatching("/v2/kv/.*"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(resultData)));

        Thread.sleep(100000);

        log.info("start_api_server end..");
    }

}
