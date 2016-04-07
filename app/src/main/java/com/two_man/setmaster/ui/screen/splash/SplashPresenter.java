package com.two_man.setmaster.ui.screen.splash;

import javax.inject.Inject;

import ru.litres.android.audio.interactor.bookfull.BookFullInteractor;
import ru.litres.android.audio.ui.base.BasePresenter;
import ru.litres.android.audio.ui.base.dialog.DialogManager;
import ru.litres.android.audio.ui.base.fragment.PerFragment;


/**
 * presenter экрана сплеша
 */
@PerFragment
public class SplashPresenter extends BasePresenter<SplashFragment> {

    private DialogManager dialogManager;
    private BookFullInteractor bookFullInteractor;

    @Inject
    public SplashPresenter(DialogManager dialogManager, BookFullInteractor bookFullInteractor) {
        this.dialogManager = dialogManager;
        this.bookFullInteractor = bookFullInteractor;
    }

    @Override
    public void onLoad() {
        super.onLoad();
        //smth
    }
}
