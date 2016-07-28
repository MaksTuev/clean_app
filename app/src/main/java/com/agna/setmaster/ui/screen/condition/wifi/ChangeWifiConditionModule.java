package com.agna.setmaster.ui.screen.condition.wifi;

import android.content.Context;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;

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

    @Provides
    @PerScreen
    ArrayList<WifiConfiguration> provideWifiNetworks(Context context){
        ArrayList<WifiConfiguration> result = new ArrayList<>();
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        List<WifiConfiguration> configurations = wifiManager.getConfiguredNetworks();
        result.addAll(configurations);
        return result;
    }
}
