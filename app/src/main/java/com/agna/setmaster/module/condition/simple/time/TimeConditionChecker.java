package com.agna.setmaster.module.condition.simple.time;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.agna.setmaster.app.PerApplication;
import com.agna.setmaster.entity.condition.DayOfWeek;
import com.agna.setmaster.entity.condition.TimeCondition;
import com.agna.setmaster.module.condition.simple.ConditionStateChangedEvent;
import com.agna.setmaster.module.condition.simple.ConditionWrapper;
import com.agna.setmaster.module.condition.simple.SimpleConditionChecker;
import com.agna.setmaster.util.TimeUtil;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.PublishSubject;
import timber.log.Timber;

/**
 *
 */
@PerApplication
public class TimeConditionChecker implements SimpleConditionChecker<TimeCondition> {
    public static final String ACTION_ALARM = "setmaster.TimeConditionChecker.alarm";
    public static final String EXTRA_CONDITION_ID = "EXTRA_CONDITION_ID";
    public static final String EXTRA_PROFILE_ID = "EXTRA_PROFILE_ID";
    public static final String EXTRA_ACTIVE = "EXTRA_ACTIVE";
    public static final String EXTRA_DAYS = "EXTRA_DAYS";
    public static final int MS_IN_DAY = 24 * 60 * 60 * 1000;


    private Context appContext;
    private AlarmManager alarmManager;
    private PublishSubject<ConditionStateChangedEvent> conditionChangedSubject = PublishSubject.create();

    @Inject
    public TimeConditionChecker(Context appContext) {
        this.appContext = appContext;
        this.alarmManager = (AlarmManager) appContext.getSystemService(Context.ALARM_SERVICE);
    }

    @Override
    public void unregister(ConditionWrapper<TimeCondition> conditionWrapper) {
        Timber.d("unregister condition: " + conditionWrapper);
        PendingIntent fromIntent = createIntent(conditionWrapper, true);
        fromIntent.cancel();
        alarmManager.cancel(fromIntent);

        PendingIntent toIntent = createIntent(conditionWrapper, false);
        toIntent.cancel();
        alarmManager.cancel(toIntent);

        ConditionStateChangedEvent event = new ConditionStateChangedEvent(
                conditionWrapper.getProfileId(), conditionWrapper.getCondition().getId(), false);
        conditionChangedSubject.onNext(event);
    }

    @Override
    public void register(ConditionWrapper<TimeCondition> conditionWrapper) {
        checkCondition(conditionWrapper);

        PendingIntent fromIntent = createIntent(conditionWrapper, true);
        alarmManager.set(AlarmManager.RTC_WAKEUP, getFirstTimeAlarm(conditionWrapper.getCondition().getFrom()), fromIntent);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                getFirstTimeAlarm(conditionWrapper.getCondition().getFrom()),
                AlarmManager.INTERVAL_DAY,
                fromIntent);

        PendingIntent toIntent = createIntent(conditionWrapper, false);
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                getFirstTimeAlarm(conditionWrapper.getCondition().getTo()),
                AlarmManager.INTERVAL_DAY,
                toIntent);
    }

    private void checkCondition(ConditionWrapper<TimeCondition> conditionWrapper) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar fromCalendar = getCalendar(conditionWrapper.getCondition().getFrom());
        Calendar toCalendar = getCalendar(conditionWrapper.getCondition().getTo());
        if (toCalendar.getTimeInMillis() < fromCalendar.getTimeInMillis()) {
            toCalendar.setTimeInMillis(toCalendar.getTimeInMillis() + MS_IN_DAY);
        }
        boolean active = false;
        if (currentCalendar.getTimeInMillis() > fromCalendar.getTimeInMillis()
                && currentCalendar.getTimeInMillis() < toCalendar.getTimeInMillis()) {
            active = true;
        }
        DayOfWeek currentDay = TimeUtil.getCurrentDayOfWeek();
        if (conditionWrapper.getCondition().getDays().contains(currentDay)) {
            ConditionStateChangedEvent event = new ConditionStateChangedEvent(
                    conditionWrapper.getProfileId(), conditionWrapper.getCondition().getId(), active);
            conditionChangedSubject.onNext(event);
        }
    }

    private Calendar getCalendar(Date date) {
        Calendar tempCalendar = Calendar.getInstance();
        tempCalendar.setTime(date);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, tempCalendar.get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, tempCalendar.get(Calendar.MINUTE));
        return calendar;
    }

    private long getFirstTimeAlarm(Date date) {
        Calendar currentCalendar = Calendar.getInstance();
        Calendar alarmCalendar = getCalendar(date);
        if (currentCalendar.getTimeInMillis() > alarmCalendar.getTimeInMillis()) {
            alarmCalendar.setTimeInMillis(alarmCalendar.getTimeInMillis() + MS_IN_DAY); //add day
        }
        Timber.d("Current: " + currentCalendar.getTimeInMillis() / 1000);
        Timber.d("alarm: " + alarmCalendar.getTimeInMillis() / 1000);
        return alarmCalendar.getTimeInMillis();
    }

    private PendingIntent createIntent(ConditionWrapper<TimeCondition> conditionWrapper, boolean active) {
        Intent intent = new Intent(appContext, TimeBroadcastReceiver.class);
        intent.putExtra(EXTRA_CONDITION_ID, conditionWrapper.getCondition().getId());
        intent.putExtra(EXTRA_DAYS, conditionWrapper.getCondition().getDays());
        intent.putExtra(EXTRA_PROFILE_ID, conditionWrapper.getProfileId());
        intent.putExtra(EXTRA_ACTIVE, active);
        int requestCode = conditionWrapper.getCondition().getId().hashCode() + (active ? 0 : 1);
        PendingIntent result = PendingIntent.getBroadcast(appContext, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return result;
    }

    @Override
    public Observable<ConditionStateChangedEvent> observeConditionStateChanged() {
        return conditionChangedSubject;
    }

    public void onAlarmReceived(Intent intent) {
        String conditionId = intent.getStringExtra(TimeConditionChecker.EXTRA_CONDITION_ID);
        String profileId = intent.getStringExtra(TimeConditionChecker.EXTRA_PROFILE_ID);
        boolean active = intent.getBooleanExtra(TimeConditionChecker.EXTRA_ACTIVE, false);
        ArrayList<DayOfWeek> days = (ArrayList<DayOfWeek>) intent.getSerializableExtra(TimeConditionChecker.EXTRA_DAYS);
        DayOfWeek currentDay = TimeUtil.getCurrentDayOfWeek();
        if (days.contains(currentDay)) {
            ConditionStateChangedEvent event = new ConditionStateChangedEvent(profileId, conditionId, active);
            conditionChangedSubject.onNext(event);
        }

    }
}
