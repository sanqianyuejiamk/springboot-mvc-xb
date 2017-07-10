package com.mengka.springboot.consul_03;

import com.orbitz.consul.Consul;
import com.orbitz.consul.StatusClient;
import lombok.extern.slf4j.Slf4j;

/**
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class consul_raft_01 {

    public static void main(String[] args) {
        StatusClient statusClient = Consul.builder().build().statusClient();
        for (String peer : statusClient.getPeers()) {
            log.info("find raft peer = {}",peer);// 127.0.0.1:8300
        }

        String leader = statusClient.getLeader();
        log.info("find raft leader = {}",leader);
    }
}
