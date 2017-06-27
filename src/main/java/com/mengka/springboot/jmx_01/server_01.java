package com.mengka.springboot.jmx_01;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import com.mengka.springboot.jmx_01.server.HelloWorld;
import com.sun.jdmk.comm.HtmlAdaptorServer;
import lombok.extern.slf4j.Slf4j;

/**
 *  启动jmx server发布服务
 *
 * @author mengka
 * @date 2017/06/27.
 */
@Slf4j
public class server_01 {

    public static void main(String[] args)throws Exception{


        /**
         * step01:
         *     在1099端口创建服务
         */
        // jdk/bin/rmiregistry.exe 9999
        Registry registry = LocateRegistry.createRegistry(Constant.rmiPort);

        /**
         * step02:
         *     获取MBeanServer
         *
         *  方法一：
         *  MBeanServerFactory.createMBeanServer(jmxServerName);
         *
         *  方法二：（jdk1.5以上版本引入）
         *  ManagementFactory.getPlatformMBeanServer();
         */
        MBeanServer mbs = MBeanServerFactory.createMBeanServer(Constant.jmxServerName);
        //或 MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

        /**
         * step03:
         *  添加htmladapter，这样就可以通过网页的方式来管理发布的MBean服务
         *  http://127.0.0.1:8082/
         */
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.setPort(8082);
        adapter.start();
        mbs.registerMBean(adapter, new ObjectName(Constant.adapterNameStr));

        /**
         * step04:
         *    绑定需要被管理的类
         */
        mbs.registerMBean(new HelloWorld(), new ObjectName(Constant.helloWorldNameStr));

        /**
         * step05:
         *   将服务绑定到固定的url上，并发布服务
         */
        JMXServiceURL url = new JMXServiceURL(Constant.serviceURL);
        JMXConnectorServer jmxConnServer = JMXConnectorServerFactory.newJMXConnectorServer(url, null, mbs);
        jmxConnServer.start();
        log.info("JMXServiceURL: " + url.toString());
        log.info("jmx server start..");
    }
}
