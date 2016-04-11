package com.two_man.setmaster.util.log;

import com.crashlytics.android.Crashlytics;
import com.two_man.setmaster.ui.base.HasName;

import timber.log.Timber;


/**
 * класс для расширенного логирования ошибок в Crashlytics
 * расширения:
 * 1. Добавляется логин пользователя
 * 2. Добавляется информация о создании и разрушении view
 * 3. Есть возможность логировать не фатальные ошибки
 */
public class LogServerUtil {

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
