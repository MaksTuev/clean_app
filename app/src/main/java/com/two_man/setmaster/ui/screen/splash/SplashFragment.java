package com.two_man.setmaster.ui.screen.splash;

import com.two_man.setmaster.ui.base.BasePresenter;
import com.two_man.setmaster.ui.base.fragment.BaseFragmentView;

/**
 * view экрана сплеша
 */
public class SplashFragment extends BaseFragmentView {

    //@Inject
    //SplashPresenter presenter;

    @Override
    protected void satisfyDependencies() {
        /*DaggerSplashComponent.builder()
                .containerActivityComponent(getBaseActivity().getContainerActivityComponent())
                .fragmentModule(new FragmentModule(this))
                .build()
                .inject(this);*/
    }

    @Override
    public BasePresenter getPresenter() {
        return null;
    }

    @Override
    public String getName() {
        return "Splash";
    }
}
