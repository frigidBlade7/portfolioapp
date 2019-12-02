package com.codedevtech.portfolioapp.di.modules;

import com.codedevtech.portfolioapp.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    // update this file with abstract methods for AndroidInjector contributing activities

    @ContributesAndroidInjector(modules = FragmentModule.class)
    abstract MainActivity contributesMainActivity();

}
