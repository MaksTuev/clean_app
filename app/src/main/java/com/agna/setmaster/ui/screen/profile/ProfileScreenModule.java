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
package com.agna.setmaster.ui.screen.profile;

import com.agna.setmaster.domain.Profile;
import com.agna.setmaster.ui.base.PerScreen;

import dagger.Module;
import dagger.Provides;

/**
 *
 */
@Module
public class ProfileScreenModule {
    private Profile profile;

    public ProfileScreenModule(Profile profile) {
        this.profile = profile;
    }

    @Provides
    @PerScreen
    public Profile provideProfile() {
        return profile;
    }
}
