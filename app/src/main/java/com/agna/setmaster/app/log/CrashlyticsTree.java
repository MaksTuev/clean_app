package com.agna.setmaster.app.log;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

import timber.log.Timber;


/**
 * отправляет логи в крашлитикс
 */
public class CrashlyticsTree extends Timber.Tree {

    private final int logPriority;

    /**
     * Создание экземпляра с приоритетом по умолчанию для логгирования в Crashlytics.
     * По умолчанию приоритет - DEBUG.
     */
    public CrashlyticsTree() {
        this(Log.DEBUG);
    }

    private CrashlyticsTree(int logPriority) {
        Crashlytics.class.getCanonicalName(); //проверка доступности Crashlytics
        this.logPriority = logPriority;
    }


    @Override
    protected void log(int priority, String tag, String message, Throwable throwable) {
        if (priority < logPriority) {
            return;
        }
        String logMessage = message;
        if (tag != null) {
            logMessage = tag + ": " + logMessage;
        }
        if (throwable != null) {
            logMessage = logMessage + "\n" + throwable.getMessage() + '\n' + Log.getStackTraceString(throwable);
        }
        try {
            LogServerUtil.logMessage(logMessage);
        } catch (Exception e) {
            e("error  " + e.getMessage() + " when send message: " + logMessage + "... with priority: " + priority);
        }
    }
}
