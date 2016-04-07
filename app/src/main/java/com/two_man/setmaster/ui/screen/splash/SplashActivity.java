package com.two_man.setmaster.ui.screen.splash;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

import ru.litres.android.audio.R;
import ru.litres.android.audio.ui.base.activity.BaseActivity;

public class SplashActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (savedInstanceState == null) {
            addFragment(R.id.container, new SplashFragment(), "AAA");
        }
    }

    protected void addFragment(int containerViewId, Fragment fragment, String tag) {
        FragmentTransaction fragmentTransaction = this.getFragmentManager().beginTransaction();
        fragmentTransaction.add(containerViewId, fragment, tag);
        fragmentTransaction.commit();
    }
}
