package com.mengka.springboot.manager;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Date;

/**
 * @author huangyy
 * @description
 * @date 2017/05/02.
 */
@Slf4j
@Service
public class LimitInfoManager {

    public void printLimitInfo(){
        log.info("您有可用额度5000.00元 [{}]", TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
    }
}
