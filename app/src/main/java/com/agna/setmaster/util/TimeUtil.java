package com.agna.setmaster.util;

import com.agna.setmaster.entity.condition.DayOfWeek;

import java.util.Calendar;

/**
 *
 */
public class TimeUtil {
    public static DayOfWeek getCurrentDayOfWeek() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        if(day == 1){ //sunday
            day = 8;
        }
        day -=2; //start from 0
        return DayOfWeek.ENUMS[day];
    }
}
