package com.two_man.setmaster.ui.screen.condition.wifi;

import android.app.Activity;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;

import com.two_man.setmaster.entity.condition.WiFiCondition;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.dialog.DialogManager;
import com.two_man.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;

import java.util.ArrayList;

import javax.inject.Inject;

/**
 *
 */
public class ChangeWifiConditionPresenter extends BasePresenter<ChangeWifiConditionActivity> {

    private DialogManager dialogManager;
    private ArrayList<WifiConfiguration> wifiNetworks;
    private WiFiCondition condition;

    @Inject
    public ChangeWifiConditionPresenter(DialogManager dialogManager,
                                        ArrayList<WifiConfiguration> wifiNetworks){

        this.dialogManager = dialogManager;
        this.wifiNetworks = wifiNetworks;
    }

    public void init(WiFiCondition condition) {
        this.condition = condition;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        getView().showNetworks(wifiNetworks);
        getView().bind(condition);
    }

    public void saveCondition(WifiConfiguration wifiConfiguration) {
        condition.setNetworkName(wifiConfiguration.SSID);
        Intent i = new Intent();
        i.putExtra(ChangeConditionBaseActivityView.EXTRA_CONDITION, condition);
        getView().goBack(Activity.RESULT_OK, i);
    }
}
