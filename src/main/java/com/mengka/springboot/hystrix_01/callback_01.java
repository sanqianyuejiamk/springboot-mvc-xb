package com.mengka.springboot.hystrix_01;

import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

/**
 * @author mengka
 * @date 2017/07/10.
 */
@Slf4j
public class callback_01 {

    public static void main(String[] args) {
        //注册观察者事件拦截
        Observable<String> fs = new HelloWorldCommand("World").observe();

        //注册结果回调事件
        fs.subscribe(new Action1<String>() {
            @Override
            public void call(String result) {
                //执行结果处理,result 为HelloWorldCommand返回的结果
                //用户对结果做二次处理.
                log.info("HelloWorldCommand result = "+result);
            }
        });

        //注册完整执行生命周期事件
        fs.subscribe(new Observer<String>() {

            /**
             *  onNext/onError完成之后最后回调
             */
            @Override
            public void onCompleted() {
                log.info("-----onCompleted-----execute onCompleted");
            }

            /**
             * 当产生异常时回调
             */
            @Override
            public void onError(Throwable e) {
                log.info("-----onError-----onError:{} ",e);
            }

            /**
             *  获取结果后回调
             *
             * @param v
             */
            @Override
            public void onNext(String v) {
                log.info("-----onNext-----" + v);
            }
        });
    }
}
