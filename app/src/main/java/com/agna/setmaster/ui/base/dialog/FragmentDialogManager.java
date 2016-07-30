/*
 * Copyright 2016 Maxim Tuev.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.agna.setmaster.ui.base.dialog;


import android.support.v4.app.Fragment;

import com.agna.setmaster.ui.base.PerScreen;

import javax.inject.Inject;

@PerScreen
public class FragmentDialogManager implements DialogManager {

    private Fragment fragment;

    @Inject
    public FragmentDialogManager(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void show(BaseDialog dialog) {
        dialog.show(fragment);
    }

    @Override
    public void show(BaseBottomSheetDialog dialog) {
        dialog.show(fragment);
    }
}
