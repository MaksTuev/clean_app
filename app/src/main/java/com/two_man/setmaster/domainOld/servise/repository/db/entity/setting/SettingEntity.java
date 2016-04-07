package com.two_man.setmaster.domainOld.servise.repository.db.entity.setting;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.two_man.setmaster.domainOld.servise.repository.db.entity.ProfileEntity;

/**
 * класс модели настроек для базы данных
 */
@DatabaseTable(tableName = "setting")
public class SettingEntity {

    @DatabaseField(id = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private SettingType type;

    @DatabaseField(canBeNull = false)
    private String jsonValue;

    @DatabaseField(foreign = true, canBeNull = false)
    private ProfileEntity profile;
}
