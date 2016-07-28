package com.agna.setmaster.module.storage.db.dao;

import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.DatabaseTableConfig;
import com.agna.setmaster.module.storage.db.BaseDao;
import com.agna.setmaster.module.storage.db.entity.ProfileObj;

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
