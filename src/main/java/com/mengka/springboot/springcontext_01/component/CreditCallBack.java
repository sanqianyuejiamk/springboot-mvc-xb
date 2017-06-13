package com.mengka.springboot.springcontext_01.component;

/**
 * @author mengka
 * @date 2017/05/14.
 */
public interface CreditCallBack {

    /**
     *回调通知授信结果
     */
    void callbackexcute(String message);

}
