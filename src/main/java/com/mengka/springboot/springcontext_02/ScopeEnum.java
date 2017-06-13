package com.mengka.springboot.springcontext_02;

import lombok.Getter;

/**
 *  容器中管理的对象scope类型
 *
 * @author mengka
 * @date 2017/05/14.
 */
@Getter
public enum ScopeEnum {

    SCOPE_SINGLETON("singleton","默认scope"),//Return a single bean instance per Spring IoC container
    SCOPE_PROTOTYPE("prototype","每次请求"),//Return a new bean instance each time when requested
    SCOPE_REQUEST("request","每个HTTP请求"),//Return a single bean instance per HTTP request
    SCOPE_SESSION("session","每个独立session"),//Return a single bean instance per HTTP session
    SCOPE_GLOBALSESSION("globalSession","每个grobal session"),//Return a single bean instance per global HTTP session
    SCOPE_TENANT("自定义scope","支持自定义scope类型");

    private String code;

    private String desc;

    ScopeEnum(String code,String desc){
        this.code = code;
        this.desc = desc;
    }
}
