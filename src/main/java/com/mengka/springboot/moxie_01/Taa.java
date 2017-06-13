package com.mengka.springboot.moxie_01;

import com.alibaba.fastjson.JSON;
import com.mengka.springboot.moxie_01.componet.TaskCreateReq;
import lombok.extern.slf4j.Slf4j;

/**
 * @author huangyy
 * @description
 * @date 2017/05/11.
 */
@Slf4j
public class Taa {

    //银行英文缩写
    public static final String BANK = "bank";

    //账户
    public static final String ACCOUNT = "account";

    //密码
    public static final String PASSWORD = "password";

    //登录类型
    public static final String LOGIN_TYPE = "login_type";

    //登录目标
    public static final String LOGIN_TARGET = "login_target";

    //爬虫执行过程中,客户是否参与(输入图片/短信验证码) 0 - sdk; 1 - html5 ; 2 - api-with-input; 3 - api-no-input
    public static final String ORIGIN = "origin";

    //用户识别信息. task/bill callback & 监控查询使用. (最多256 bytes)
    public static final String USER_ID = "user_id";

    /**
     *  测试一下
     *
     * @param args
     */
    public static void main(String[] args){
        //Step1 创建任务
        TaskCreateReq req = TaskCreateReq.builder()
                .add(BANK, "ICBC")
                .add(ACCOUNT, "62220212028680960")
                .add(PASSWORD, "123456")
                .add(LOGIN_TYPE, "2")
                .add(LOGIN_TARGET, "4")
                .add(USER_ID, "12345680")
                .add(ORIGIN, "2")
                .build();

        log.info("TaskCreateReq = {}", JSON.toJSONString(req));
    }
}