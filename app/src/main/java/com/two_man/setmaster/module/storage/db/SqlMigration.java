package com.two_man.setmaster.module.storage.db;

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
