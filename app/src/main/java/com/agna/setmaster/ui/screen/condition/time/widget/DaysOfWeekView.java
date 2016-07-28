package com.agna.setmaster.ui.screen.condition.time.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.CheckedTextView;
import android.widget.LinearLayout;

import com.agna.setmaster.R;
import com.agna.setmaster.entity.condition.DayOfWeek;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class DaysOfWeekView extends LinearLayout {
    private List<CheckedTextView> dayViews = new ArrayList<>();

    public DaysOfWeekView(Context context) {
        super(context);
        initView(context);
    }

    public DaysOfWeekView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DaysOfWeekView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    public DaysOfWeekView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {
        inflate(context, R.layout.days_of_week_view_layout, this);
        dayViews.addAll(Arrays.asList(
                (CheckedTextView)findViewById(R.id.monday),
                (CheckedTextView)findViewById(R.id.tuesday),
                (CheckedTextView)findViewById(R.id.wednesday),
                (CheckedTextView)findViewById(R.id.thursday),
                (CheckedTextView)findViewById(R.id.friday),
                (CheckedTextView)findViewById(R.id.saturday),
                (CheckedTextView)findViewById(R.id.sunday)));
        for (CheckedTextView view : dayViews){
            view.setOnClickListener(v ->{
                CheckedTextView checkedTextView = (CheckedTextView)v;
                checkedTextView.setChecked(!checkedTextView.isChecked());
            });
        }
    }

    public void show(List<DayOfWeek> daysOfWeek){
        for(DayOfWeek day: daysOfWeek){
            dayViews.get(day.ordinal()).setChecked(true);
        }
    }

    public ArrayList<DayOfWeek> getDays(){
        ArrayList<DayOfWeek> result = new ArrayList<>();
        for (int i = 0; i < dayViews.size(); i++){
            CheckedTextView v = dayViews.get(i);
            if(v.isChecked()){
                result.add(DayOfWeek.ENUMS[i]);
            }
        }
        return result;
    }


    public void setAccentColor(int accentColor) {
        for(CheckedTextView v: dayViews){
            int redColor = ContextCompat.getColor(getContext(), R.color.profile_active_bg);
            Drawable drawable;
            if(accentColor == redColor){
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.day_of_week_red_bg);
            } else {
                drawable = ContextCompat.getDrawable(getContext(), R.drawable.day_of_week_blue_bg);
            }
            v.setBackground(drawable);
        }
    }
}
