package com.mengka.springboot.jmx_02;

/**
 * @author mengka
 * @date 2017/06/28.
 */
public interface Constant {

    int rmiPort = 1098;

    String jmxServerName = "mkJMXServer";

    String serviceURL = "service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + jmxServerName;

    String helloWorldNameStr = "com.jmx:type=ServerMonitor";
}
