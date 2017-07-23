package com.mengka.springboot.rxJava_01;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import java.util.Date;

/**
 * @author mengka
 * @date 2017/07/23.
 */
@Slf4j
public class observer_01 {

    public static void main(String[] args) {


        Observable<String> observable = Observable.unsafeCreate(new Observable.OnSubscribe<String>() {

            /**
             *  发送数据
             *
             * @param subscriber
             */
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    String message = "Hi，water!!！[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
                    log.info("observable call message = {}",message);

                    Thread.sleep(3000);

                    subscriber.onNext(message);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    log.error("call error!", e);
                }
            }
        });

        Observer<String> observer = new Observer<String>() {

            /**
             *  数据接收完成时调用
             *
             */
            @Override
            public void onCompleted() {
                log.info("数据接受完成");
            }

            /**
             *  发生错误调用
             *
             * @param e
             */
            @Override
            public void onError(Throwable e) {
                log.info("数据接受错误！");
            }

            /**
             *  接收数据
             *
             * @param s
             */
            @Override
            public void onNext(String s) {
                log.info("receiver message from sender: {}", s);
            }
        };


        observable.subscribe(observer);
    }
}
