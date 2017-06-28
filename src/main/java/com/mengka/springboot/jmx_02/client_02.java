package com.mengka.springboot.jmx_02;

import com.mengka.springboot.jmx_02.server.HelloWorldMBean;
import lombok.extern.slf4j.Slf4j;
import javax.management.JMX;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author mengka
 * @date 2017/06/28.
 */
@Slf4j
public class client_02 {

    public static void main(String[] args) throws Exception {
        //创建JMX客户端，并连接到指定地址的服务端
        JMXServiceURL url = new JMXServiceURL(Constant.serviceURL);
        Map<String, Object> env = new HashMap<String, Object>();
//        env.put( "jmx.remote.credentials", new String[] { "admin", "admin123" } );
        JMXConnector conn = JMXConnectorFactory.connect(url, env);

        //代理方式调用jmx服务
        HelloWorldMBean helloWorldService = JMX.newMBeanProxy(conn.getMBeanServerConnection(),
                new ObjectName(Constant.helloWorldNameStr), HelloWorldMBean.class);
        String memory = helloWorldService.getMemory();
        long time = helloWorldService.getUptime();
        log.info("client_02 helloWorldService memory = {}", memory);
        log.info("client_02 helloWorldService time = {}", time);

        //关闭连接
        conn.close();
    }
}
