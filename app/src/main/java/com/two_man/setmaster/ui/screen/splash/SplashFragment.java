package com.two_man.setmaster.ui.screen.splash;

import javax.inject.Inject;

import ru.litres.android.audio.ui.base.BasePresenter;
import ru.litres.android.audio.ui.base.fragment.BaseFragmentView;
import ru.litres.android.audio.ui.base.fragment.FragmentModule;

/**
 * view экрана сплеша
 */
public class SplashFragment extends BaseFragmentView {

    @Inject
    SplashPresenter presenter;

    @Override
    protected void satisfyDependencies() {
        DaggerSplashComponent.builder()
                .containerActivityComponent(getBaseActivity().getContainerActivityComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }

    @Override
    public String getName() {
        return "Splash";
    }
}
