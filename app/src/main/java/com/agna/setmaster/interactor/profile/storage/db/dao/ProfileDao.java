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
package com.agna.setmaster.interactor.profile.storage.db.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.agna.setmaster.interactor.profile.storage.db.BaseDao;
import com.agna.setmaster.interactor.profile.storage.db.entity.ProfileObj;

import java.sql.SQLException;

/**
 *
 */
public class ProfileDao extends BaseDao<ProfileObj, String> {

    public ProfileDao(Class<ProfileObj> dataClass) throws SQLException {
        super(dataClass);
    }

    public ProfileDao(ConnectionSource connectionSource, Class<ProfileObj> dataClass) throws SQLException {
        super(connectionSource, dataClass);
    }

    public ProfileDao(ConnectionSource connectionSource, DatabaseTableConfig<ProfileObj> tableConfig) throws SQLException {
        super(connectionSource, tableConfig);
    }

    @Override
    protected ProfileObj getData(String id) throws SQLException {
        ProfileObj profile = queryForId(id);
        return profile;
    }
}
