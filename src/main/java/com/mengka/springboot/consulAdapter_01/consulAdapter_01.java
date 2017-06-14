package com.mengka.springboot.consulAdapter_01;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mengka
 * @date 2017/06/14.
 */
@Slf4j
public class consulAdapter_01 {

    public static void main(String[] args) throws Exception{
        log.info("consulAdapter_01 start..");

        //获取测试数据
        List<Map<String, Object>> values = getConsulAdaptorData();

        //获取key/value数据
        Map<String, Object> properties = new LinkedHashMap<String, Object>();
        values.stream()
                .filter(i -> i.get("Value") != null)
                .forEach(i -> {

                    properties.put((String) i.get("Key"),
                            new String(Base64.getDecoder().decode((String) i.get("Value"))));

                });

        //打印结果
        log.info("properties = {}",properties);

        log.info("consulAdapter_01 end..");
    }

    /**
     *  获取测试数据
     *
     * @return
     * @throws Exception
     */
    public static List<Map<String, Object>> getConsulAdaptorData() throws Exception {
        Path responsePath = Paths.get(Thread.currentThread().getContextClassLoader().
                getResource("get_kv_response.json").toURI());
        byte[] resultData = Files.readAllBytes(responsePath);

        List<Map<String, Object>> values = JSON.parseObject(resultData, List.class);
        return values;
    }
}
