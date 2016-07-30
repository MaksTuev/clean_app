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
package com.agna.setmaster.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

//утилы, упрощающие работу с Settings
public class SettingsUtil {
    public static final String EMPTY_STRING_SETTING = "";
    public static final int EMPTY_INT_SETTING = -1;

    public static String getString(Context context, String key) {
        return getString(context, getDefaultSharedPreferences(context), key);
    }

    public static void putString(Context context, String key, String value) {
        putString(context, getDefaultSharedPreferences(context), key, value);
    }

    public static void putInt(Context context, String key, int value) {
        putInt(context, getDefaultSharedPreferences(context), key, value);
    }

    public static int getInt(Context context, String key) {
        return getInt(context, getDefaultSharedPreferences(context), key);
    }

    public static void putBoolean(Context context, String key, boolean value) {
        putBoolean(context, getDefaultSharedPreferences(context), key, value);
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getBoolean(context, getDefaultSharedPreferences(context), key, defaultValue);
    }

    public static String getString(Context context, SharedPreferences sp, String key) {
        return sp.getString(key, EMPTY_STRING_SETTING);
    }

    public static void putString(Context context, SharedPreferences sp, String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        saveChanges(context, editor);
    }

    public static void putInt(Context context, SharedPreferences sp, String key, int value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, value);
        saveChanges(context, editor);
    }

    public static int getInt(Context context, SharedPreferences sp, String key) {
        return sp.getInt(key, EMPTY_INT_SETTING);
    }

    public static void putBoolean(Context context, SharedPreferences sp, String key, boolean value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, value);
        saveChanges(context, editor);
    }

    public static boolean getBoolean(Context context, SharedPreferences sp, String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    private static void saveChanges(Context context, SharedPreferences.Editor editor) {
        editor.commit();
    }

    private static SharedPreferences getDefaultSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

}
