package com.mengka.springboot.rxJava_02;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import java.util.Date;

/**
 * @author mengka
 * @date 2017/07/23.
 */
@Slf4j
public class observable_02 {

    public static void main(String[] args){
        /**
         * from()将会依次调用：
         *  onNext(message1);
         *  onNext(message2);
         *  onNext(message3);
         *  onCompleted();
         */
        String message1 = "mengka AAA..["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String message2 = "mengka BBB..["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String message3 = "mengka CCC..["+ TimeUtil.toDate(new Date(),TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String[] words = {message1,message2,message3};
        Observable<String> observable = Observable.from(words);

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
