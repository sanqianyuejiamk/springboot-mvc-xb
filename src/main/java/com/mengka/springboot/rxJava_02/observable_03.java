package com.mengka.springboot.rxJava_02;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import java.util.Date;

/**
 *  subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber；
 *
 * @author mengka
 * @date 2017/07/23.
 */
@Slf4j
public class observable_03 {

    public static void main(String[] args) {
        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                log.info("onNextAction call message = {}", s);
            }
        };
        Action1<Throwable> onErrorAction = new Action1<Throwable>() {
            // onError()
            @Override
            public void call(Throwable throwable) {
                // Error handling
                log.info("onErrorAction call!", throwable);
            }
        };
        Action0 onCompletedAction = new Action0() {
            // onCompleted()
            @Override
            public void call() {
                log.info("onCompletedAction call..");
            }
        };

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

        /**
         * subscribe() 还支持不完整定义的回调，RxJava 会自动根据定义创建出 Subscriber；
         */
        // 自动创建 Subscriber ，并使用 onNextAction 来定义 onNext()
        observable.subscribe(onNextAction);
        // 自动创建 Subscriber ，并使用 onNextAction 和 onErrorAction 来定义 onNext() 和 onError()
        observable.subscribe(onNextAction, onErrorAction);
        // 自动创建 Subscriber ，并使用 onNextAction、 onErrorAction 和 onCompletedAction 来定义 onNext()、 onError() 和 onCompleted()
        observable.subscribe(onNextAction, onErrorAction, onCompletedAction);
    }
}
