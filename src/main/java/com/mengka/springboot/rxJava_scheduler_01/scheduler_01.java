package com.mengka.springboot.rxJava_scheduler_01;

import com.mengka.springboot.util.TimeUtil;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

/**
 * @author mengka
 * @date 2017/07/23.
 */
@Slf4j
public class scheduler_01 {

    public static void main(String[] args) {

        Action1<String> onNextAction = new Action1<String>() {
            // onNext()
            @Override
            public void call(String s) {
                log.info("onNextAction call message = {}", s);
                try {
                    String content = s;
                    String path = "/Users/hyy044101331/tmp/springboot-mvc-xb/result" + Math.random() + ".txt";

                    Files.write(Paths.get(path), content.getBytes());
                } catch (Exception e) {
                    log.error("call file write error!", e);
                }
            }
        };

        /**
         * from()将会依次调用：
         *  onNext(message1);
         *  onNext(message2);
         *  onNext(message3);
         *  onCompleted();
         */
        String message1 = "mengka AAA..[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String message2 = "mengka BBB..[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String message3 = "mengka CCC..[" + TimeUtil.toDate(new Date(), TimeUtil.FORMAT_YYYY_MM_DD_HH_MM_SS);
        String[] words = {message1, message2, message3};

        Observable.from(words)
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())//指定的操作将在 Android 主线程运行
                .subscribe(onNextAction);


    }
}
