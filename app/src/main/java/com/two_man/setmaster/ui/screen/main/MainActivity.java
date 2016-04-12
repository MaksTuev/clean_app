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
    }

    protected void addFragment(int containerViewId, android.support.v4.app.Fragment fragment, String tag) {
        android.support.v4.app.FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.commit();
    }
}
