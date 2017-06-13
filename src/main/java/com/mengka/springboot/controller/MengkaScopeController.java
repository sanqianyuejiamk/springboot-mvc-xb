package com.mengka.springboot.controller;

import com.mengka.springboot.model.MengkaApplyRsp;
import com.mengka.springboot.springcontext_01.CallbackManager;
import com.mengka.springboot.springcontext_02.MengkaScopeManager;
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
@RequestMapping(value = "/app/v1/scope")
public class MengkaScopeController {

    @Autowired
    private MengkaScopeManager mengkaScopeManager;

    /**
     *  http://127.0.0.1:8076/app/v1/scope/prototype
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/prototype", method = RequestMethod.GET)
    @ResponseBody
    public MengkaApplyRsp callback() throws Exception {
        log.info("调用 springcontext_02接口，请求参数:{}");

        /**
         *  测试scope实例
         *
         */
        mengkaScopeManager.mengkaScope();

        MengkaApplyRsp applyRsp = new MengkaApplyRsp();
        applyRsp.setResult(true);
        applyRsp.setMessage("scope实例执行成功["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        return applyRsp;
    }
}
