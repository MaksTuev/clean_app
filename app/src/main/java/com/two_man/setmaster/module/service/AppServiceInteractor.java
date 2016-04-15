package com.two_man.setmaster.module.service;

import android.content.Context;
import android.content.Intent;

/**
 *
 */
public class AppServiceInteractor {
    private Context context;

    public AppServiceInteractor(Context context) {
        this.context = context;
    }

    public void start(){
        Intent i  = new Intent(context, AppService.class);
        context.startService(i);
    }
}
