package com.agna.setmaster.module.storage.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.agna.setmaster.entity.Profile;
import com.agna.setmaster.module.storage.db.dao.ProfileDao;

/**
 * TODO Maks Tuev create flexible storage
 */
@DatabaseTable(tableName = "profiles", daoClass = ProfileDao.class)
public class ProfileObj {

    @DatabaseField(id = true, columnName = "book_id")
    private String id;
    @DatabaseField(columnName = "profile", dataType = DataType.SERIALIZABLE)
    private Profile profile;

    public ProfileObj() {
    }

    public ProfileObj(Profile profile) {
        id = profile.getId();
        this.profile = profile;
    }


    public Profile getProfile() {
        profile.clearActiveState();
        return profile;
    }
}
