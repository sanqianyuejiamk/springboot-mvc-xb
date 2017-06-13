package com.mengka.springboot.springcontext_02.component;

import com.mengka.springboot.springcontext_02.ScopeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * {@link ScopeEnum}
 *  SCOPE:"session"
 *  spring容器会为每个独立的session创建属于他们自己的全新的对象实例；
 *
 * @author mengka
 * @date 2017/05/14.
 */
@Slf4j
@Scope(value="session", proxyMode = ScopedProxyMode.TARGET_CLASS)
//@Scope("prototype")
@Component
public class MengkaComponent {

    public void foo(){
        log.info("MengkaComponent foo..[{}]",this.hashCode());
    }
}
