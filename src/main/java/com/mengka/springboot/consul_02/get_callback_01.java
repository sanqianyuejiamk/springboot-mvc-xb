package com.mengka.springboot.consul_02;

import com.google.common.base.Optional;
import com.mengka.springboot.util.TimeUtil;
import com.orbitz.consul.Consul;
import com.orbitz.consul.KeyValueClient;
import com.orbitz.consul.async.ConsulResponseCallback;
import com.orbitz.consul.model.ConsulResponse;
import com.orbitz.consul.model.kv.Value;
import com.orbitz.consul.option.QueryOptions;
import lombok.extern.slf4j.Slf4j;
import java.math.BigInteger;
import java.util.Date;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author mengka
 * @date 2017/07/05.
 */
@Slf4j
public class get_callback_01 {

    public static void main(String[] args) throws Exception{
        Consul consul = Consul.builder().build(); // connect to Consul on localhost
        final KeyValueClient kvClient = consul.keyValueClient();

        kvClient.putValue("foo", "bar[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS));
        log.info("put consul data[foo].");

        ConsulResponseCallback<Optional<Value>> callback = new ConsulResponseCallback<Optional<Value>>() {

            AtomicReference<BigInteger> index = new AtomicReference<BigInteger>(null);

            @Override
            public void onComplete(ConsulResponse<Optional<Value>> consulResponse) {
                if (consulResponse.getResponse().isPresent()) {
                    Value v = consulResponse.getResponse().get();
                    log.info("Value is: {}", v.getValue());
                    log.info("decode Value is: {}", v.getValueAsString().get());
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

        kvClient.getValue("foo", QueryOptions.blockSeconds(5, new BigInteger("0")).build(), callback);
        Thread.sleep(10000);
    }
}
