package com.mengka.springboot.bean_01;

/**
 * @author mengka
 * @date 2017/09/20.
 */
public class MengkaService {

    private BaicaiService baicaiService;

    public BaicaiService getBaicaiService() {
        return baicaiService;
    }

    public void setBaicaiService(BaicaiService baicaiService) {
        this.baicaiService = baicaiService;
    }
}
