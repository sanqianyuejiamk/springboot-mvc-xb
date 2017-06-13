package com.mengka.springboot.springcontext_01.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Slf4j
@Component(value = "FUND_CALLBACK")
public class FundCallback extends AbstractCreditCallBack{

    @Override
    public void callbackexcute(String message) {
        log.info("FUND_CALLBACK callbackexcute message = {}",message);
    }
}
