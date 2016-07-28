package com.agna.setmaster.ui.base.dialog;

/**
 * Сущность, отвечающая за показывание и скрывание диалогов
 */
public interface DialogManager {
    void show(BaseDialog dialog);
    void show(BaseBottomSheetDialog dialog);

}
