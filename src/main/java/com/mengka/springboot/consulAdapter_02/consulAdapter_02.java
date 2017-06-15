package com.mengka.springboot.consulAdapter_02;

import com.mengka.springboot.consulAdapter_02.component.ConsulAdapter;
import com.mengka.springboot.consulAdapter_02.component.ConsulProperties;
import lombok.extern.slf4j.Slf4j;
import java.util.Map;

/**
 *  获取consul配置
 *
 * @author mengka
 * @date 2017/06/15.
 */
@Slf4j
public class consulAdapter_02 {


    public static void main(String[] args) {
        /**step01*/
        //启动consul，http://127.0.0.1:8500/ui/#/mkdc1/services

        /**step02*/
        //获取consul配置，http://127.0.0.1:8500/v1/kv/root/flyover/property
        ConsulAdapter adapter = new ConsulAdapter(new ConsulProperties());
        Map<String, Object> properties = adapter.get("root/flyover", true);
        log.info("consulAdapter_02 properties = {}",properties);

        //获取consul配置，http://127.0.0.1:8500/v1/kv/service/springboot-mvc-xb/test/config
        Map<String, Object> properties2 = adapter.get("service/springboot-mvc-xb/test", true);
        log.info("consulAdapter_02 properties2 = {}",properties2);
    }
}
