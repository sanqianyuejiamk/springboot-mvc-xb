package com.mengka.springboot.rxJava_03;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import java.util.ArrayList;
import java.util.List;

/**
 * RxJava使用
 * 过滤所有的用户，只返回名字是mengka的用户；
 * 返回用户的同桌；
 *
 * @author mengka
 * @date 2017/08/07.
 */
@Slf4j
public class map_03 {

    public static void main(String[] args) {

        Observable<List<UserDO>> observable = Observable.unsafeCreate(new Observable.OnSubscribe<List<UserDO>>() {

            @Override
            public void call(Subscriber<? super List<UserDO>> subscriber) {
                List<UserDO> list = new ArrayList<UserDO>();
                list.add(new UserDO("044101331", "mengka", new UserDO("044101321", "mengka的同桌")));
                list.add(new UserDO("044101332", "xiafeng", new UserDO("044101322", "xiafeng的同桌")));

                subscriber.onNext(list);
            }
        });

        Action1<UserDO> onNextAction = new Action1<UserDO>() {
            // onNext()
            @Override
            public void call(UserDO userDO) {
                log.info("onNextAction call userDO = {}", JSON.toJSONString(userDO));
            }
        };

        /**
         * 过滤所有的用户，只返回名字是mengka的用户；
         * 返回用户的同桌；
         */
        observable.flatMap(new Func1<List<UserDO>, Observable<UserDO>>() {
            @Override
            public Observable<UserDO> call(List<UserDO> userDOs) {
                return observable.from(userDOs);
            }
        }).filter(new Func1<UserDO, Boolean>() {
            @Override
            public Boolean call(UserDO userDO) {
                return userDO.getName().equals("mengka");//名字是mengka的用户
            }
        }).map(new Func1<UserDO, UserDO>() {
            @Override
            public UserDO call(UserDO userDO) {
                return userDO.getDeskmate();//用户的同桌
            }
        }).subscribe(onNextAction);
    }
}
