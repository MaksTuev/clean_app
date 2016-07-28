package com.agna.setmaster.module.service;

import android.content.Context;
import android.content.Intent;

import com.agna.setmaster.app.PerApplication;

import javax.inject.Inject;

/**
 *
 */
@PerApplication
public class AppServiceInteractor {
    private Context context;

    @Inject
    public AppServiceInteractor(Context context) {
        this.context = context;
    }

    public void start(){
        Intent i  = new Intent(context, AppService.class);
        context.startService(i);
    }
}
