package com.mengka.springboot.consul_01;

import com.orbitz.consul.AgentClient;
import com.orbitz.consul.Consul;
import lombok.extern.slf4j.Slf4j;

/**
 *  注册服务到consul
 *
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class register_01 {

    public static void main(String[] args) throws Exception {
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        AgentClient agentClient = consul.agentClient();

        String serviceName = "MyService";
        String serviceId = "1";

        agentClient.register(8080, 3L, serviceName, serviceId); // registers with a TTL of 3 seconds
        while(true) {
            Thread.sleep(2000);
            agentClient.pass(serviceId); // check in with Consul, serviceId required only.  client will prepend "service:" for service level checks.
        }

// Note that you need to continually check in before the TTL expires, otherwise your service's state will be marked as "critical".
    }
}
