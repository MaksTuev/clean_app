/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

    public void start() {
        Intent i = new Intent(context, AppService.class);
        context.startService(i);
    }
}
