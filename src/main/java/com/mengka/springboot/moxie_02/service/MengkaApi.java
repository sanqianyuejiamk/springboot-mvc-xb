package com.mengka.springboot.moxie_02.service;

import com.mengka.springboot.model.MengkaApplyRsp;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author huangyy
 * @description
 * @date 2017/05/11.
 */
public interface MengkaApi {

    @GET("/app/v1/mengka/application")
    Call<MengkaApplyRsp> apply();
}
