package com.two_man.setmaster.ui.base;

import android.os.Bundle;
import android.support.annotation.NonNull;

/**
 * базовый класс для презентера
 *
 * имеет методы, соответствующие жизненному циклу view
 * при пересоздании View, заново создается и презентер
 *
 * @param <V> тип View
 *           если V не интерфейс, то использование методов View, относящихся к андроид фреймворку запрещено
 */
public class BasePresenter<V> {

    private V view;

    public void attachView(V view){
        this.view = view;
    }

    protected V getView(){
        return view;
    }

    public void onLoad() {
    }

    public void onDestroy() {
    }

    public void onRestore(@NonNull Bundle savedInstanceState) {
    }

    public void onSave(@NonNull Bundle outState) {
    }
}
