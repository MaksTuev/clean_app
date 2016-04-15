package com.two_man.setmaster.module.storage.db.entity;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.module.storage.db.dao.ProfileDao;

/**
 *
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
