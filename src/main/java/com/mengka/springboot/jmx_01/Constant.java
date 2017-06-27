package com.mengka.springboot.jmx_01;

/**
 * @author mengka
 * @date 2017/06/27.
 */
public interface Constant {

    int rmiPort = 1099;

    String jmxServerName = "TestJMXServer";

    String serviceURL = "service:jmx:rmi:///jndi/rmi://localhost:" + rmiPort + "/" + jmxServerName;

    String adapterNameStr = jmxServerName + ":name=htmladapter";

    String helloWorldNameStr = jmxServerName + ":name=HelloWorld";
}
