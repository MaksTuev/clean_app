package com.two_man.setmaster.module.storage.db.entity.condition;

/**
 *
 */
public class WiFiConditionObj extends ConditionObj {
    private String networkName;

    public WiFiConditionObj() {
    }


    private WiFiConditionObj(String id, boolean active, String networkName) {
        super(id);
        this.networkName = networkName;
    }

    public String getNetworkName() {
        return networkName;
    }

}