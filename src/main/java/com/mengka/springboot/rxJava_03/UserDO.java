package com.mengka.springboot.rxJava_03;

import lombok.Data;

/**
 * @author mengka
 * @date 2017/08/07.
 */
@Data
public class UserDO {

    private String id;

    private String name;

    private UserDO deskmate;//同桌

    public UserDO(String id,String name){
        this.id = id;
        this.name = name;
    }

    public UserDO(String id,String name,UserDO deskmate){
        this.id = id;
        this.name = name;
        this.deskmate = deskmate;
    }
}
