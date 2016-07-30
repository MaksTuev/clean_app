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
