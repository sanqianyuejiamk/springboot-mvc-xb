package com.mengka.springboot.springcontext_01;

import lombok.Getter;

/**
 *  回调组件
 *
 * @author mengka
 * @date 2017/05/14.
 */
@Getter
public enum CallbackBizEnum {

    DEFAULT_CALLBACK("DEFAULT_CALLBACK","默认回调通知器"),
    FUND_CALLBACK("FUND_CALLBACK","公积金回调");

    private String code;

    private String desc;

    CallbackBizEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
