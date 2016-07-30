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
package com.agna.setmaster.ui.screen.condition.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.agna.setmaster.R;
import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.domain.condition.TimeCondition;
import com.agna.setmaster.ui.base.BasePresenter;
import com.agna.setmaster.ui.base.activity.ActivityModule;
import com.agna.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;
import com.agna.setmaster.ui.screen.condition.time.widget.DaysOfWeekView;
import com.agna.setmaster.ui.util.ProfileViewUtil;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

/**
 *
 */
public class ChangeTimeConditionActivity extends ChangeConditionBaseActivityView {
    public static final String EXTRA_CONDITION = "EXTRA_CONDITION";
    public static final String EXTRA_ACCENT_COLOR = "EXTRA_ACCENT_COLOR";
    @Inject
    ChangeTimeConditionPresenter presenter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    private TextView name;
    private TextView fromText;
    private TextView fromValue;
    private TextView toText;
    private TextView toValue;
    private DaysOfWeekView weekdaysView;
    private View headerContainer;
    private int accentColor;
    private View saveBtn;
    private View declineBtn;

    @Override
    protected void satisfyDependencies() {
        TimeCondition condition = (TimeCondition) getIntent().getSerializableExtra(EXTRA_CONDITION);
        DaggerChangeTimeConditionComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(getApplicationComponent())
                .timeConditionModule(new TimeConditionModule(condition))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accentColor = getIntent().getIntExtra(EXTRA_ACCENT_COLOR, 0);
        findViews();
        initViews();
        initListeners();
    }

    private void initListeners() {
        saveBtn.setOnClickListener(v -> save());
        declineBtn.setOnClickListener(v -> goBack());
        fromValue.setOnClickListener(v -> showTimePickerDialog(getDate(fromValue), this::onFromTimePicked));
        toValue.setOnClickListener(v -> showTimePickerDialog(getDate(toValue), this::onToTimePicked));
    }

    private Date getDate(TextView textView) {
        try {
            return dateFormat.parse(textView.getText().toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    private void save() {
        presenter.saveCondition(
                getDate(fromValue),
                getDate(toValue),
                weekdaysView.getDays());
    }

    @Override
    public void onBackPressed() {
        save();
    }

    private void initViews() {
        fromText.setTextColor(accentColor);
        toText.setTextColor(accentColor);
        weekdaysView.setAccentColor(accentColor);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
    }

    private void findViews() {
        saveBtn = findViewById(R.id.save_btn);
        declineBtn = findViewById(R.id.decline_btn);
        name = (TextView) findViewById(R.id.condition_name);
        fromText = (TextView) findViewById(R.id.time_condition_from_text);
        fromValue = (TextView) findViewById(R.id.time_condition_from_time);
        toText = (TextView) findViewById(R.id.time_condition_to_text);
        toValue = (TextView) findViewById(R.id.time_condition_to_time);
        weekdaysView = (DaysOfWeekView) findViewById(R.id.weekdays);
        headerContainer = findViewById(R.id.condition_header);
    }

    public void bind(TimeCondition condition) {
        fromValue.setText(dateFormat.format(condition.getFrom()));
        toValue.setText(dateFormat.format(condition.getTo()));
        weekdaysView.show(condition.getDays());
    }

    private void showTimePickerDialog(Date date, TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        TimePickerDialog dialog = TimePickerDialog.newInstance(
                listener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true);
        dialog.dismissOnPause(true);
        dialog.enableSeconds(false);
        dialog.setAccentColor(accentColor);
        dialog.setThemeDark(false);
        dialog.show(getFragmentManager(), TimePickerDialog.class.getSimpleName());
    }

    private void onFromTimePicked(RadialPickerLayout radialPickerLayout, int hour, int min, int sec) {
        Date date = getDate(hour, min);
        fromValue.setText(dateFormat.format(date));
    }

    private void onToTimePicked(RadialPickerLayout radialPickerLayout, int hour, int min, int sec) {
        Date date = getDate(hour, min);
        toValue.setText(dateFormat.format(date));
    }

    private Date getDate(int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(0);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        Date date = calendar.getTime();
        return date;
    }

    @Override
    protected int getContentView() {
        return R.layout.change_time_condition_activity;
    }

    @Override
    public String getName() {
        return "ChangeTimeConditionActivity";
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    public static void start(Activity activity, TimeCondition condition, Profile profile) {
        Intent i = new Intent(activity, ChangeTimeConditionActivity.class);
        i.putExtra(EXTRA_CONDITION, condition);
        i.putExtra(EXTRA_ACCENT_COLOR, ProfileViewUtil.getProfileAccentColor(activity, profile));
        activity.startActivityForResult(i, 1);
    }

}
