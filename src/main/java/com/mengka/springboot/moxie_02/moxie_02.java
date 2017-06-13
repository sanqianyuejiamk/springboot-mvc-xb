package com.mengka.springboot.moxie_02;


import com.alibaba.fastjson.JSON;
import com.mengka.springboot.model.MengkaApplyRsp;
import com.mengka.springboot.moxie_02.component.AuthToken;
import com.mengka.springboot.moxie_02.component.MengkaApiFactory;
import com.mengka.springboot.moxie_02.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import com.mengka.springboot.moxie_02.service.MengkaApi;
import retrofit2.Call;

import java.io.IOException;

/**
 * @author huangyy
 * @description
 * @date 2017/05/11.
 */
@Slf4j
public class moxie_02 {

    private MengkaApiFactory mengkaApiFactory;

    @Before
    public void setUp() {
        mengkaApiFactory = MengkaApiFactory.builder()
                .withAuthToken(new AuthToken(Constants.APIKEY, Constants.TOKEN))
                .withBaseUrl("http://127.0.0.1:8076")
                .build();
    }

    @Test
    public void testApplication() throws IOException {
        // Step1 创建任务
        MengkaApi mengkaApi = mengkaApiFactory.newApi(MengkaApi.class);
        // Step2 调用申请接口
        Call<MengkaApplyRsp> call = mengkaApi.apply();
        MengkaApplyRsp mengkaApplyRsp = call.execute().body();
        log.info("mengkaApi apply mengkaApplyRsp = {}", JSON.toJSONString(mengkaApplyRsp));
    }
}
