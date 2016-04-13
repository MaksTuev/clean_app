package com.two_man.setmaster.entity.condition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/**
 *
 */
public class TimeCondition extends Condition {
    private Date from = new Date(0);
    private Date to = new Date(60 * 1000);
    private ArrayList<DayOfWeek> days = new ArrayList<>(Arrays.asList(DayOfWeek.ENUMS));


    public TimeCondition() {
    }

    public TimeCondition(Date from, Date to, ArrayList<DayOfWeek> days) {
        this.from = from;
        this.to = to;
        this.days = days;
    }

    private TimeCondition(String id, boolean active, Date from, Date to, ArrayList<DayOfWeek> days) {
        super(id, active);
        this.from = from;
        this.to = to;
        this.days = days;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public void setDays(ArrayList<DayOfWeek> days) {
        this.days = days;
    }

    public Date getFrom() {
        return from;
    }

    public Date getTo() {
        return to;
    }

    public ArrayList<DayOfWeek> getDays() {
        return days;
    }

    @Override
    public Condition clone() {
        return new TimeCondition(getId(), isActive(), getFrom(), getTo(), getDays());
    }


}
