package com.mengka.springboot.hystrix_dashboard_01;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author mengka
 * @date 2017/07/11.
 */
@Slf4j
public class JettyServer {

    private int port;
    private ExecutorService executorService = Executors.newFixedThreadPool(1);
    private Server server = null;

    public void init() {
        try {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        //绑定8080端口,加载HystrixMetricsStreamServlet并映射url
                        server = new Server(8080);
                        WebAppContext context = new WebAppContext();
                        context.setContextPath("/");
                        context.addServlet(HystrixMetricsStreamServlet.class, "/hystrix.stream");
                        context.setResourceBase(".");
                        server.setHandler(context);
                        server.start();
                        server.join();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void destory() {
        if (server != null) {
            try {
                server.stop();
                server.destroy();
                log.warn("jettyServer stop and destroy!");
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }
}
