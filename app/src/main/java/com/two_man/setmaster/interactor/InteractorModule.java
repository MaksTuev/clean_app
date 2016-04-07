package com.two_man.setmaster.interactor;

import dagger.Module;
import dagger.Provides;
import ru.litres.android.audio.interactor.bookfull.BookFullInteractor;
import ru.litres.android.audio.module.player.PlayerBookStateProvider;
import ru.litres.android.audio.module.repository.BookRepository;
import ru.litres.android.audio.ui.app.PerApplication;

@Module
public class InteractorModule {

    @Provides
    @PerApplication
    BookFullInteractor provideBookFullInteractor(BookRepository bookRepository,
                                                 PlayerBookStateProvider playerBookStateProvider){
        return new BookFullInteractor(bookRepository, playerBookStateProvider);
    }
}
