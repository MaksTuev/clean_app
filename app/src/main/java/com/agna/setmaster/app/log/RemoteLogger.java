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
package com.agna.setmaster.app.log;

import com.crashlytics.android.Crashlytics;
import com.agna.setmaster.ui.base.HasName;


/**
 * класс для расширенного логирования ошибок в Crashlytics
 * расширения:
 * 1. Добавляется логин пользователя
 * 2. Добавляется информация о создании и разрушении view
 * 3. Есть возможность логировать не фатальные ошибки
 */
public class RemoteLogger {

    public static void setUser(String userId, String username) {
        if (Crashlytics.getInstance() != null) {
            Crashlytics.getInstance().core.setUserIdentifier(userId);
            Crashlytics.getInstance().core.setUserName(username);
        }
    }

    public static void clearUser() {
        if (Crashlytics.getInstance() != null) {
            Crashlytics.getInstance().core.setUserIdentifier("");
            Crashlytics.getInstance().core.setUserName("");
        }
    }

    public static void logViewCreated(HasName view) {
        if (view != null) {
            logMessage("View " + view.getName() + " created");
        }
    }

    public static void logViewDestroyed(HasName view) {
        if (view != null) {
            logMessage("View " + view.getName() + " Destroyed");
        }
    }

    public static void logError(Throwable e) {
        try {
            Crashlytics.getInstance().core.logException(e);
        } catch (Throwable err) {
            //ignored
        }
    }

    public static void logMessage(String message) {
        if (Crashlytics.getInstance() != null && message != null) {
            Crashlytics.getInstance().core.log(message);
        }
    }

    public static void setCustomKey(String key, String value) {
        if (Crashlytics.getInstance() != null) {
            Crashlytics.getInstance().core.setString(key, value);
        }
    }
}
