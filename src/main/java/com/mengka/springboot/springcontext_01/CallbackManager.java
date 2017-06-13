package com.mengka.springboot.springcontext_01;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.mengka.springboot.springcontext_01.component.CreditCallBack;
import java.util.Date;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Slf4j
@Service
public class CallbackManager {

    @Autowired
    private ApplicationContext context;

    public void callbackAutoCredit() {
        /**默认回调器*/
        CreditCallBack defaultCallBack = context.getBean(CallbackBizEnum.DEFAULT_CALLBACK.getCode(),CreditCallBack.class);
        defaultCallBack.callbackexcute("test message["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));

        /**fund回调器*/
        CreditCallBack fundCallBack = context.getBean(CallbackBizEnum.FUND_CALLBACK.getCode(),CreditCallBack.class);
        fundCallBack.callbackexcute("test message["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
    }
}
