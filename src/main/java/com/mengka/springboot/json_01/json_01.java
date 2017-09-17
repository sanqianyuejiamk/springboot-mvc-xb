package com.mengka.springboot.json_01;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

import java.math.BigDecimal;

/**
 * @author mengka
 * @date 2017/08/21.
 */
@Slf4j
public class json_01 {

    public static void main(String[] args){

        MengkaDO mengkaDO = new MengkaDO();
        mengkaDO.setId("044101331");
        mengkaDO.setName("门卡");
        mengkaDO.setAmount(new BigDecimal(100.999));

        JSONObject jsonObject = JSONObject.fromObject(mengkaDO);

        String id = jsonObject.getString("id");
        log.info("jsonObject id = {}",id);

        BigDecimal amount = (BigDecimal)jsonObject.get("amount");
        log.info("jsonObject amount = {}",amount);

        String json = JSONObject.fromObject(mengkaDO).toString();
        JSONObject jsonObject1 = JSONObject.fromObject(json);
        String name = jsonObject1.getString("name");
        log.info("jsonObject name = {}",name);
    }
}
