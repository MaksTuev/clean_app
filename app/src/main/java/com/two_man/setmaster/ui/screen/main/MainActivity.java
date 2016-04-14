package com.two_man.setmaster.ui.screen.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.two_man.setmaster.R;
import com.two_man.setmaster.ui.base.activity.BaseActivity;

/**
 *
 */
public class MainActivity extends BaseActivity {

    public static void start(Activity activity) {
        Intent i = new Intent(activity, MainActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.finish();
        activity.startActivity(i);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null){
            addFragment(R.id.container, MainFragmentView.newInstance(), MainFragmentView.class.getSimpleName());
        }

        /*AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, TimeBroadcastReceiver.class);
        //Intent i = new Intent(appContext, MainActivity.class);
        PendingIntent result = PendingIntent.getBroadcast(this, 1111, intent, 0);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MILLISECOND, 2000);
        am.set(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), result);

        Intent intent2 = new Intent(this, TimeBroadcastReceiver.class);
        PendingIntent result2 = PendingIntent.getBroadcast(this, 11112, intent2, 0);
        Calendar c2 = Calendar.getInstance();
        c2.add(Calendar.MILLISECOND, 5000);
        am.set(AlarmManager.RTC_WAKEUP, c2.getTimeInMillis(), result2);*/
    }

    protected void addFragment(int containerViewId, android.support.v4.app.Fragment fragment, String tag) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.commit();
    }
}
