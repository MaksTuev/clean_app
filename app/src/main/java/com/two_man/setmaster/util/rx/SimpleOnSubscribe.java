package com.two_man.setmaster.util.rx;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class SimpleOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private Subscriber<? super T> subscriber;

    @Override
    public void call(Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
        subscriber.onStart();
    }

    public void emit(T obj){
        if(subscriber!=null){
            subscriber.onNext(obj);
        }
    }
}
