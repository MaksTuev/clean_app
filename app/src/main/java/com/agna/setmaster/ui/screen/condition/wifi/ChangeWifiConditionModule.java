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
package com.agna.setmaster.ui.screen.condition.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

import com.agna.setmaster.entity.condition.WiFiCondition;
import com.agna.setmaster.ui.base.PerScreen;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ChangeWifiConditionModule {

    private WiFiCondition condition;

    public ChangeWifiConditionModule(WiFiCondition condition) {
        this.condition = condition;
    }

    @Provides
    @PerScreen
    WiFiCondition provideWiFiCondition(){
        return condition;
    }

    @Provides
    @PerScreen
    ArrayList<WifiConfiguration> provideWifiNetworks(Context context) {
        ArrayList<WifiConfiguration> result = new ArrayList<>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configurations = wifiManager.getConfiguredNetworks();
        result.addAll(configurations);
        return result;
    }
}
