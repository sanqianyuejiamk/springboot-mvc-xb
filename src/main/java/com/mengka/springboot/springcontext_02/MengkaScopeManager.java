package com.mengka.springboot.springcontext_02;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.mengka.springboot.springcontext_02.component.MengkaScopeClient;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Slf4j
@Service
public class MengkaScopeManager {

    @Autowired
    private ApplicationContext context;

    public void mengkaScope()throws Exception{
        /**scope实例一*/
        MengkaScopeClient mengkaScopeClient1 = context.getBean(MengkaScopeClient.class,"mengka baicai");
        String name1 = mengkaScopeClient1.getName();
        log.info("-----scope----- mengkaScopeClient1[{}] name = {}",mengkaScopeClient1.hashCode(),name1);

        Thread.sleep(3000);

        /**scope实例二*/
        MengkaScopeClient mengkaScopeClient2 = context.getBean(MengkaScopeClient.class,"mengka qingcai");
        String name2 = mengkaScopeClient2.getName();
        log.info("-----scope----- mengkaScopeClient2[{}] name = {}",mengkaScopeClient2.hashCode(),name2);
    }
}
