package com.mengka.springboot.controller;

import com.mengka.springboot.model.MengkaApplyRsp;
import com.mengka.springboot.springcontext_01.CallbackManager;
import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Slf4j
@RestController
@RequestMapping(value = "/app/v1/notify")
public class NotifyCreditResultController {

    @Autowired
    private CallbackManager callbackManager;

    /**
     *  http://127.0.0.1:8076/app/v1/notify/c1
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/c1", method = RequestMethod.GET)
    @ResponseBody
    public MengkaApplyRsp callback() throws Exception {
        log.info("调用回调器接口，请求参数:{}");

        /**
         *  测试callback component
         *
         */
        callbackManager.callbackAutoCredit();

        MengkaApplyRsp applyRsp = new MengkaApplyRsp();
        applyRsp.setResult(true);
        applyRsp.setMessage("回调器执行成功["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        return applyRsp;
    }
}
