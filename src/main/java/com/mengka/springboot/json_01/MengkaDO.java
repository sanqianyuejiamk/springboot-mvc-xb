package com.mengka.springboot.json_01;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author mengka
 * @date 2017/08/21.
 */
@Data
public class MengkaDO {

    private String id;

    private String name;

    private BigDecimal amount;
}
