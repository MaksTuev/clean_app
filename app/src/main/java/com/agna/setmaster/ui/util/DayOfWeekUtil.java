package com.agna.setmaster.ui.util;

import android.content.Context;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.condition.DayOfWeek;

import java.util.ArrayList;

/**
 *
 */
public class DayOfWeekUtil {
    public static String getStringValue(Context context, DayOfWeek day) {
        switch (day) {
            case MONDAY:
                return context.getString(R.string.monday);
            case TUESDAY:
                return context.getString(R.string.tuesday);
            case WEDNESDAY:
                return context.getString(R.string.wednesday);
            case THURSDAY:
                return context.getString(R.string.thursday);
            case FRIDAY:
                return context.getString(R.string.friday);
            case SATURDAY:
                return context.getString(R.string.saturday);
            case SUNDAY:
                return context.getString(R.string.sunday);
            default:
                throw new IllegalArgumentException();
        }
    }

    public static String daysToString(ArrayList<DayOfWeek> days, Context context) {
        String result = "";
        if (days.size() == 0) {
            return result;
        }
        DayOfWeek startRange = days.get(0);
        result += DayOfWeekUtil.getStringValue(context, startRange);
        DayOfWeek prevDay = startRange;
        for (int i = 1; i < days.size(); i++) {
            DayOfWeek day = days.get(i);
            if (day.ordinal() - prevDay.ordinal() != 1) {
                if (prevDay != startRange) {
                    if (prevDay.ordinal() - startRange.ordinal() == 1) {
                        result += ", " + DayOfWeekUtil.getStringValue(context, prevDay) + ", ";
                    } else {
                        result += "-" + DayOfWeekUtil.getStringValue(context, prevDay) + ", ";
                    }
                } else {
                    result += ", ";
                }
                startRange = day;
                result += DayOfWeekUtil.getStringValue(context, day);
            }
            prevDay = day;
        }

        if (prevDay != startRange) {
            if (prevDay.ordinal() - startRange.ordinal() == 1) {
                result += ", " + DayOfWeekUtil.getStringValue(context, prevDay);
            } else {
                result += "-" + DayOfWeekUtil.getStringValue(context, prevDay);
            }
        }
        return result;

    }
}
