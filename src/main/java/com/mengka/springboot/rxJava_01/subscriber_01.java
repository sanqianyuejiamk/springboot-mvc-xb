package com.mengka.springboot.rxJava_01;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import java.util.Date;

/**
 * Subscriber
 * 1）实现了Observer抽象类；
 * 2）Subscriber 对 Observer 接口进行了一些扩展，但他们的基本使用方式是完全一样的；
 *
 * @author mengka
 * @date 2017/07/23.
 */
@Slf4j
public class subscriber_01 {

    public static void main(String[] args) {

        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String message = "Hi，water!!！[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
                    log.info("observable2 call message = {}", message);

                    Thread.sleep(3000);

                    subscriber.onNext(message);
                } catch (Exception e) {
                    log.error("call error!", e);
                }
            }
        });

        Subscriber<String> subscriber = new Subscriber<String>() {

            @Override
            public void onCompleted() {
                log.info("数据接受完成");
            }

            @Override
            public void onError(Throwable e) {
                log.info("数据接受错误!", e);
            }

            @Override
            public void onNext(String s) {
                log.info("receiver message from sender: {}", s);
            }
        };

        observable.subscribe(subscriber);
    }
}
