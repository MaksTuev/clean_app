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
package com.agna.setmaster.interactor.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.agna.setmaster.app.dagger.PerApplication;
import com.agna.setmaster.interactor.storage.db.entity.ProfileObj;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import javax.inject.Inject;

import rx.Observable;
import timber.log.Timber;

/**
 *
 */
@PerApplication
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "litres_listen.db";
    private static final int DATABASE_VERSION = 5;

    private Context appContext;

    @Inject
    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Timber.i(DataBaseHelper.class.getSimpleName() + ".ctr");
        appContext = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            Timber.i(DataBaseHelper.class.getSimpleName() + ".onCreate()");

            TableUtils.createTableIfNotExists(connectionSource, ProfileObj.class);

        } catch (SQLException e) {
            Timber.e(e, "Can't create database: " + DataBaseHelper.class.getSimpleName());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TransactionManager tm = new TransactionManager(connectionSource);
            tm.callInTransaction(() -> {
                for (SqlMigration migration : SqlMigrationStorage.migrations) {
                    migration.execute(appContext, oldVersion, newVersion, database, connectionSource, this);
                }
                return null;
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        super.close();
    }

    @SuppressWarnings("unchecked")
    public <T, ID> Observable<T> getAsync(final Class<T> dataClass, final ID id) {
        BaseDao dao = safeGetBaseDao(dataClass);
        assert dao != null;
        return dao.getAsync(id);
    }

    public <T> Observable<T> getAsync(Class<T> dataClass) {
        return getAsync(dataClass, null);
    }

    @SuppressWarnings("unchecked")
    public <T> Observable<T> saveAsync(final T data) {
        BaseDao dao = safeGetBaseDao(data.getClass());
        assert dao != null;
        return dao.saveAsync(data);
    }

    @SuppressWarnings("unchecked")
    public <T, ID> T get(Class<T> dataClass, ID id) {
        BaseDao dao = safeGetBaseDao(dataClass);
        assert dao != null;
        return (T) dao.get(id);
    }

    public <T> T get(Class<T> dataClass) {
        return get(dataClass, null);
    }

    @SuppressWarnings("unchecked")
    public <T> T saveSync(final T data) {
        BaseDao dao = safeGetBaseDao(data.getClass());
        assert dao != null;
        return (T) dao.save(data);
    }

    public <T> boolean deleteFirst(Class<T> dataClass) {
        BaseDao dao = safeGetBaseDao(dataClass);
        assert dao != null;
        return dao.deleteFirst();
    }

    public <T> boolean clear(Class<T> dataClass) {
        BaseDao dao = safeGetBaseDao(dataClass);
        assert dao != null;
        try {
            TableUtils.clearTable(connectionSource, dataClass);
        } catch (SQLException e) {
            Timber.e(e, "Couldn't clear table for type: " + dataClass.getSimpleName());
            return false;
        }
        return true;
    }

    private <T> BaseDao safeGetBaseDao(Class<T> dataClass) {
        BaseDao dao;
        try {
            dao = getDao(dataClass);
        } catch (SQLException e) {
            Timber.e(e, "Couldn't create DAO for type: " + dataClass.getSimpleName());
            return null;
        }
        return dao;
    }

    public <D extends Dao<T, ?>, T> D safeGetDao(Class<T> dataClass) {
        D dao;
        try {
            dao = getDao(dataClass);
        } catch (SQLException e) {
            Timber.e(e, "Couldn't create DAO for type: " + dataClass.getSimpleName());
            return null;
        }
        return dao;
    }
}
