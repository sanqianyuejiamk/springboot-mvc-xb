package com.mengka.springboot.consul_01;

import com.orbitz.consul.Consul;
import com.orbitz.consul.cache.ConsulCache;
import com.orbitz.consul.cache.ServiceHealthCache;
import com.orbitz.consul.cache.ServiceHealthKey;
import com.orbitz.consul.model.health.ServiceHealth;
import lombok.extern.slf4j.Slf4j;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class subHealthyChange_02 {

    public static void main(String[] args) throws Exception {

        String serviceName = "springboot-mvc-xb8077";

        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        ServiceHealthCache svHealth = ServiceHealthCache.newCache(consul.healthClient(), serviceName);

        /**
         *  监听服务健康状态变化事件
         */
        final List<Map<ServiceHealthKey, ServiceHealth>> events = new ArrayList<>();
        svHealth.addListener(new ConsulCache.Listener<ServiceHealthKey, ServiceHealth>() {
            @Override
            public void notify(Map<ServiceHealthKey, ServiceHealth> newValues) {
                events.add(newValues);
            }
        });

        svHealth.start();
        svHealth.awaitInitialized(1000, TimeUnit.MILLISECONDS);

        /*
           path = 172.20.10.2:8077 , serviceid = springboot-mvc-xb8077-PREPRODUCT-8077

           node=Node{
                node=server-17220,
                address=172.20.10.2,
                taggedAddresses=TaggedAddresses{
                    wan=172.20.10.2
                }
            },
            service=Service{
                id=springboot-mvc-xb8077-PREPRODUCT-8077,
                service=springboot-mvc-xb8077,
                tags=[

                ],
                address=masters,
                port=8077
            },
         */
        Map<ServiceHealthKey, ServiceHealth> event0 = events.get(0);

        for (Map.Entry<ServiceHealthKey, ServiceHealth> kv : event0.entrySet()) {
            log.info("path = {}:{} , serviceid = {}", kv.getKey().getHost(), kv.getKey().getPort(), kv.getKey().getServiceId());
            log.info("ServiceHealth = {}", kv.getValue().toString());
        }
    }
}
