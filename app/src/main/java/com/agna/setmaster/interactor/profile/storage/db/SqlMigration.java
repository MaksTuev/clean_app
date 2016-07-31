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
package com.agna.setmaster.interactor.profile.storage.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

/**
 *
 */
public abstract class SqlMigration {

    private final static String TAG = SqlMigration.class.getSimpleName();

    public int baseVersion;

    public SqlMigration(int value) {
        baseVersion = value;
    }

    public void execute(Context appContext, int oldVer, int newVer, SQLiteDatabase db, ConnectionSource connectionSource, DataBaseHelper databaseHelper)
            throws SQLException {
        if (oldVer < baseVersion && baseVersion <= newVer) {
            Log.d(TAG, "Executing database migration, baseVersion = " + baseVersion);
            apply(appContext, db, connectionSource, databaseHelper);
        }
    }

    protected abstract void apply(Context appContext, SQLiteDatabase db, ConnectionSource connectionSource, DataBaseHelper databaseHelper) throws SQLException;
}
