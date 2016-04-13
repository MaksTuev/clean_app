package com.two_man.setmaster.ui.screen.condition.time;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.two_man.setmaster.R;
import com.two_man.setmaster.entity.Profile;
import com.two_man.setmaster.entity.condition.TimeCondition;
import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.activity.ActivityModule;
import com.two_man.setmaster.ui.screen.condition.ChangeConditionBaseActivityView;
import com.two_man.setmaster.ui.screen.condition.time.widget.DaysOfWeekView;
import com.two_man.setmaster.ui.util.ProfileViewUtil;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

/**
 *
 */
public class ChangeTimeConditionActivity extends ChangeConditionBaseActivityView {
    public static final String EXTRA_CONDITION = "EXTRA_CONDITION";
    public static final String EXTRA_ACCENT_COLOR = "EXTRA_ACCENT_COLOR";
    @Inject
    ChangeTimeConditionPresenter presenter;

    SimpleDateFormat dateFormat = new SimpleDateFormat("hh:mm");

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
        DaggerChangeTimeConditionComponent.builder()
                .activityModule(new ActivityModule(this))
                .appComponent(getApplicationComponent())
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

    private Date getDate(TextView textView){
        try {
            return dateFormat.parse(textView.getText().toString());
        }catch (ParseException e){
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
        //headerContainer.setBackgroundColor(accentColor);
        fromText.setTextColor(accentColor);
        toText.setTextColor(accentColor);
        weekdaysView.setAccentColor(accentColor);
    }

    @Override
    public void initPresenter() {
        super.initPresenter();
        TimeCondition condition = (TimeCondition)getIntent().getSerializableExtra(EXTRA_CONDITION);
        presenter.init(condition);
    }

    private void findViews() {
        saveBtn = findViewById(R.id.save_btn);
        declineBtn = findViewById(R.id.decline_btn);
        name = (TextView)findViewById(R.id.condition_name);
        fromText = (TextView)findViewById(R.id.time_condition_from_text);
        fromValue = (TextView)findViewById(R.id.time_condition_from_time);
        toText = (TextView)findViewById(R.id.time_condition_to_text);
        toValue = (TextView)findViewById(R.id.time_condition_to_time);
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
        Date date = new Date(hour*60*60*1000+ min*60*1000);
        fromValue.setText(dateFormat.format(date));
    }



    private void onToTimePicked(RadialPickerLayout radialPickerLayout, int hour, int min, int sec) {
        Date date = new Date(hour*60*60*1000+ min*60*1000);
        toValue.setText(dateFormat.format(date));
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
