package com.two_man.setmaster.util.rx;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;

/**
 *
 */
public class SimpleOnSubscribe<T> implements Observable.OnSubscribe<T> {

    private List<Subscriber<? super T>> subscribers = new ArrayList<>();

    @Override
    public void call(Subscriber<? super T> subscriber) {
        this.subscribers.add(subscriber);
        subscriber.onStart();
    }

    public void emit(T obj){
        for(int i = 0; i<subscribers.size(); i++){
            Subscriber<? super T> subscriber = subscribers.get(i);
            if(subscriber.isUnsubscribed()){
                subscribers.remove(i);
                i--;
            } else {
                subscriber.onNext(obj);
            }
        }
    }
}
