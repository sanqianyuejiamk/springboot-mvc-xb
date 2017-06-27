package com.mengka.springboot.jmx_01;

import com.mengka.springboot.jmx_01.server.HelloWorldMBean;
import lombok.extern.slf4j.Slf4j;
import javax.management.MBeanServerConnection;
import javax.management.MBeanServerInvocationHandler;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import javax.management.Attribute;
import java.util.Iterator;
import java.util.Set;

/**
 *  启动jmx client调用服务
 *
 * @author mengka
 * @date 2017/06/27.
 */
@Slf4j
public class client_01 {

    public static void main(String[] args)throws Exception{
        log.info("jmx client start..");

        /**
         * step01:
         *   创建JMX客户端，并连接到指定地址的服务端
         */
        JMXServiceURL url = new JMXServiceURL(Constant.serviceURL);
        JMXConnector jmxc = JMXConnectorFactory.connect(url, null);

        //Get an MBeanServerConnection
        MBeanServerConnection mbsc = jmxc.getMBeanServerConnection();

        //Get domains from MBeanServer
        String domains[] = mbsc.getDomains();
        for (int i = 0; i < domains.length; i++) {
            log.info("Domain[" + i + "] = " + domains[i]);
        }

        //Query MBean names
        Set names = mbsc.queryNames(null, null);
        for (Iterator i = names.iterator(); i.hasNext(); ) {
            log.info("ObjectName = " + (ObjectName) i.next());
        }

        /**
         * step02:
         *  调用jmx服务
         */
        ObjectName stdMBeanName = new ObjectName(Constant.helloWorldNameStr);
        mbsc.setAttribute(stdMBeanName, new Attribute("Name","mengka AAA"));
        mbsc.invoke(stdMBeanName, "printHello", null, null);
        mbsc.invoke(stdMBeanName, "printHello", new Object[] { "HelloKity" }, new String[] { String.class.getName() });

        /**
         * step03:
         *  代理方式调用jmx服务
         */
        HelloWorldMBean proxy = (HelloWorldMBean) MBeanServerInvocationHandler.newProxyInstance(mbsc, stdMBeanName, HelloWorldMBean.class, false);
        proxy.setName("mengka BBB");
        String name = proxy.getName();
        String result = proxy.printHello();
        log.info("HelloWorldMBean name = {}, result = {}",name,result);

        /**
         * step04:
         *   关闭连接
         */
        jmxc.close();
    }
}
