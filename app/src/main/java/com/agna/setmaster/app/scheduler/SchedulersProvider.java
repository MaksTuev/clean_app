package com.agna.setmaster.app.scheduler;

import com.agna.setmaster.app.dagger.PerApplication;

import javax.inject.Inject;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 *
 */
@PerApplication
public class SchedulersProvider {

    @Inject
    public SchedulersProvider() {
    }

    public Scheduler main(){
        return AndroidSchedulers.mainThread();
    }

    public Scheduler worker(){
        return Schedulers.io();
    }
}
