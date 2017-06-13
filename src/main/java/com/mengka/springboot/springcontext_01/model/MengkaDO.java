package com.mengka.springboot.springcontext_01.model;

import lombok.Data;

/**
 * @author mengka
 * @date 2017/05/14.
 */
@Data
public class MengkaDO {

    private String id;

    private String name;

    public MengkaDO(String id,String name){
        this.id = id;
        this.name = name;
    }
}
