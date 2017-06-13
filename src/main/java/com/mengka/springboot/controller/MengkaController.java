package com.mengka.springboot.controller;

import com.mengka.springboot.component.RedisComponent;
import com.mengka.springboot.model.MengkaApplyRsp;
import com.mengka.springboot.model.bank.BankBillShoppingSheet;
import com.mengka.springboot.model.bank.BankBillShoppingSheetList;
import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author mengka
 * @description
 * @date 2017/05/11.
 */
@Slf4j
@RestController
@RequestMapping(value = "/app/v1/mengka")
public class MengkaController {

    @Autowired
    private RedisComponent redisComponent;

    @Autowired
    private Environment env;

    @Value("${mengka.test.name}")
    private String appNameTwo;

    /**
     *  mengka API申请服务
     *
     *  http://127.0.0.1:8077/app/v1/mengka/application
     *
     * @return
     * @throws Exception
     */
//    @ApiOperation(value = "申请服务")
    @RequestMapping(value = "/application", method = RequestMethod.GET)
//    @ApiImplicitParams({@ApiImplicitParam(paramType = "header", name = HttpHeaders.AUTHORIZATION, required = true, value = "service token", dataType = "string", defaultValue = AppConsts.SAMPLE_SERVICE_TOKEN)})
    @ResponseBody
    public MengkaApplyRsp apply() throws Exception {
        log.info("调用mengka API申请服务接口，请求参数:{}");

        redisComponent.set("k11","a test...");

        String result = (String)redisComponent.get("k11");



        String appName = env.getProperty("mengka.test.name");
        log.info("-----appName = [{}]",appName);

        log.info("-----appNameTwo = [{}]",appNameTwo);

        MengkaApplyRsp applyRsp = new MengkaApplyRsp();
        applyRsp.setResult(true);
        applyRsp.setMessage("申请成功["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        return applyRsp;
    }

    /**
     *  查询账单流水
     *
     * @return
     */
    @RequestMapping(value = "/bills/{billid}/expense-records", method = RequestMethod.GET)
    @ResponseBody
    public BankBillShoppingSheetList getBillRecords(@PathVariable String billid){
        log.info("调用mengka API查询账单流水接口，请求参数:{}",billid);

        //账单流水明细
        List<BankBillShoppingSheet> shoppingSheets = new ArrayList<BankBillShoppingSheet>();
        BankBillShoppingSheet shoppingSheet1 = new BankBillShoppingSheet();
        shoppingSheet1.setBankId(62220001L);
        shoppingSheet1.setBillMonth(TimeUtil.toDate("2017-05","yyyy-MM"));
        shoppingSheet1.setPostDate(new Date());
        shoppingSheet1.setTransDate(TimeUtil.toDate("2017-05-01 12:00:00",TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        shoppingSheets.add(shoppingSheet1);
        BankBillShoppingSheet shoppingSheet2 = new BankBillShoppingSheet();
        shoppingSheet2.setBankId(62220002L);
        shoppingSheet2.setBillMonth(TimeUtil.toDate("2017-06","yyyy-MM"));
        shoppingSheet2.setPostDate(new Date());
        shoppingSheet2.setTransDate(TimeUtil.toDate("2017-06-05 13:00:00",TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        shoppingSheets.add(shoppingSheet2);
        //账单汇总
        BankBillShoppingSheetList shoppingSheetList = new BankBillShoppingSheetList();
        shoppingSheetList.setTotalSize(2);
        shoppingSheetList.setSize(2);
        shoppingSheetList.setShoppingSheets(shoppingSheets);
        return shoppingSheetList;
    }

}
