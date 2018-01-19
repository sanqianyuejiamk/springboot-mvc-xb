package com.mengka.springboot.consul_02;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.mengka.springboot.util.TimeUtil;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.async.ConsulResponseCallback;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class get_callback_01 {

    public static void main(String[] args) throws Exception {
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        final KeyValueClient kvClient = consul.keyValueClient();

        kvClient.putValue("foo3", "bar[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        log.info("put consul data[foo].");

        ConsulResponseCallback<Optional<Value>> callback = new ConsulResponseCallback<Optional<Value>>() {

            AtomicReference<BigInteger> index = new AtomicReference<BigInteger>(null);

            @Override
            public void onComplete(ConsulResponse<Optional<Value>> consulResponse) {
                if (consulResponse.getResponse().isPresent()) {
                    Value v = consulResponse.getResponse().get();
                    log.info("Value is: {}", v.getValue());
                    log.info("decode Value is: {}", v.getValueAsString().get());

                    try {
                        HashMap map = Maps.newHashMap();
                        String propString = v.getValueAsString().get();

                        Properties properties = new Properties();
                        properties.load(new StringReader(propString));
                        properties.entrySet().forEach((e) -> {
                            map.put((String) e.getKey(), e.getValue());
                            if (((String) e.getKey()).startsWith("logging.level.") && LoggerFactory.getILoggerFactory() instanceof LoggerContext) {
                                LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

                                try {
                                    String ex = StringUtils.substringAfter((String) e.getKey(), "logging.level.");
                                    if (StringUtils.isBlank(ex)) {
                                        ex = "ROOT";
                                    }

                                    context.getLogger(ex).setLevel(Level.valueOf((String) e.getValue()));
                                } catch (Throwable var4) {
                                    log.error("配置项{}不符合规范", e.getKey() + "=" + e.getValue());
                                }
                            }

                        });
                        log.info("map = " + JSON.toJSONString(map));
                    } catch (Exception e) {
                        log.error("onComplete error!", e);
                    }
                }

                index.set(consulResponse.getIndex());
                watch();
            }

            void watch() {
                kvClient.getValue("foo", QueryOptions.blockSeconds(5, index.get()).build(), this);
            }

            @Override
            public void onFailure(Throwable throwable) {
                log.error("Error encountered", throwable);
                watch();
            }
        };

//        kvClient.getValue("foo", QueryOptions.blockSeconds(5, new BigInteger("0")).build(), callback);
//        kvClient.getValue("foo2", QueryOptions.blockSeconds(5, new BigInteger("0")).build(), callback);

        Optional<String> optional = kvClient.getValueAsString("foo2");
        log.info("optional value = " + optional.get());

        Thread.sleep(10000);
    }
}
