package com.two_man.setmaster.domainOld.servise.repository.db.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.two_man.setmaster.domainOld.servise.repository.db.entity.setting.SettingEntity;

import java.util.List;

/**
 *
 */
@DatabaseTable(tableName = "profile")
public class ProfileEntity {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private int imageResId;

    @ForeignCollectionField(eager = true)
    private List<ConditionSetEntity> conditionSets;

    @ForeignCollectionField(eager = true)
    private List<SettingEntity> settings;

}
