package com.mengka.springboot.consul_01;

import com.alibaba.fastjson.JSON;
import com.orbitz.consul.Consul;
import com.orbitz.consul.HealthClient;
import com.orbitz.consul.model.health.Node;
import com.orbitz.consul.model.health.Service;
import com.orbitz.consul.model.health.ServiceHealth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import java.util.List;

/**
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class findAvailableService_01 {

    public static void main(String[] args){
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        HealthClient healthClient = consul.healthClient();

        List<ServiceHealth> nodes = healthClient.getHealthyServiceInstances("springboot-mvc-xb8077").getResponse();

        log.info("nodes = {}", JSON.toJSONString(nodes));

        if(CollectionUtils.isEmpty(nodes)){
            return;
        }
        Node node = nodes.get(0).getNode();
        Service service = nodes.get(0).getService();
        String address = node.getAddress();
        String serviceName = service.getService();
        int port = service.getPort();
        log.info("serviceName:{} , url = {}:{}",serviceName,address,port);
    }
}
