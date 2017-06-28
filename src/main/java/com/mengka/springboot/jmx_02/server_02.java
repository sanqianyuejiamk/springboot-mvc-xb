package com.mengka.springboot.jmx_02;

import com.mengka.springboot.jmx_02.server.HelloWorld;
import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.ManagementFactory;
import java.rmi.registry.LocateRegistry;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengka
 * @date 2017/06/28.
 */
@Slf4j
public class server_02 {

    public static void main(String[] args) throws Exception {
        // 指定端口
        LocateRegistry.createRegistry(1098);

        //获取MBeanServer,绑定需要被管理的类
        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        ObjectName monitorName = new ObjectName(Constant.helloWorldNameStr);
        server.registerMBean(new HelloWorld(), monitorName);

        //将服务绑定到固定的url上，并发布服务
        JMXServiceURL url = new JMXServiceURL(Constant.serviceURL);
        Map<String, Object> env = new HashMap<String, Object>();
        JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, env, server);
        jmxConnServer.start();
        log.info("JMXServiceURL: " + url.toString());
        log.info("jmx server start..");
    }
}
