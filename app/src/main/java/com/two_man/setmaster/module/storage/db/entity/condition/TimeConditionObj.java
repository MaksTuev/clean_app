package com.two_man.setmaster.module.storage.db.entity.condition;

import com.two_man.setmaster.entity.condition.DayOfWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 */
public class TimeConditionObj extends ConditionObj {
    private long from;
    private Date to;
    private ArrayList<DayOfWeek> days = new ArrayList<>(Arrays.asList(DayOfWeek.ENUMS));


    public TimeConditionObj() {
    }

    private TimeConditionObj(String id, boolean active, Date from, Date to, ArrayList<DayOfWeek> days) {
        this.to = to;
        this.days = days;
    }

}
