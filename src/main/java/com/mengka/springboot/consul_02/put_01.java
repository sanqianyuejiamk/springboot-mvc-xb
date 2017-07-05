package com.mengka.springboot.consul_02;

import com.mengka.springboot.util.TimeUtil;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import lombok.extern.slf4j.Slf4j;
import java.util.Date;

/**
 *  使用consul存储key/value数据
 *
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class put_01 {

    public static void main(String[] args) {
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        KeyValueClient kvClient = consul.keyValueClient();

        kvClient.putValue("foo", "bar[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));

        String value = kvClient.getValueAsString("foo").get(); // bar
        log.info("get consul data key = foo , value = {}", value);
    }
}
