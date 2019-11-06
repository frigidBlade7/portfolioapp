package com.codedevtech.portfolioapp.di.modules;

import com.codedevtech.portfolioapp.fragments.AuthenticationFragment;
import com.codedevtech.portfolioapp.fragments.DashboardFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    //todo update this file with abstract methods for AndroidInjector contributing fragments

    @ContributesAndroidInjector
    abstract AuthenticationFragment contributesAuthenticationFragment();


}
