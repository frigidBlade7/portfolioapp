package com.codedevtech.portfolioapp.di.modules;

import com.codedevtech.portfolioapp.SplashScreenFragment;
import com.codedevtech.portfolioapp.fragments.AuthenticationExtrasBottomSheet;
import com.codedevtech.portfolioapp.fragments.AuthenticationFragment;
import com.codedevtech.portfolioapp.fragments.CompleteProfileFragment;
import com.codedevtech.portfolioapp.fragments.DashboardFragment;
import com.codedevtech.portfolioapp.fragments.EditProfileFragment;
import com.codedevtech.portfolioapp.fragments.ExploreFragment;
import com.codedevtech.portfolioapp.fragments.FeedFragment;
import com.codedevtech.portfolioapp.fragments.MessagesFragment;
import com.codedevtech.portfolioapp.fragments.NewPostFragment;
import com.codedevtech.portfolioapp.fragments.OnboardingFragment;
import com.codedevtech.portfolioapp.fragments.ProfileFragment;
import com.codedevtech.portfolioapp.fragments.RegistrationFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {
    // update this file with abstract methods for AndroidInjector contributing fragments

    @ContributesAndroidInjector
    abstract AuthenticationFragment contributesAuthenticationFragment();

    @ContributesAndroidInjector
    abstract AuthenticationExtrasBottomSheet contributesAuthenticationExtrasBottomSheet();


    @ContributesAndroidInjector
    abstract RegistrationFragment contributesRegistrationFragment();

    @ContributesAndroidInjector
    abstract CompleteProfileFragment contributesCompleteProfileFragment();

    @ContributesAndroidInjector
    abstract DashboardFragment contributesDashboardFragment();


    @ContributesAndroidInjector
    abstract OnboardingFragment contributesOnboardingFragment();

    @ContributesAndroidInjector
    abstract SplashScreenFragment splashScreenFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment profileFragment();


    @ContributesAndroidInjector
    abstract EditProfileFragment editProfileFragment();

    @ContributesAndroidInjector
    abstract FeedFragment feedFragment();

    @ContributesAndroidInjector
    abstract MessagesFragment messagesFragment();


    @ContributesAndroidInjector
    abstract ExploreFragment exploreFragment();

    @ContributesAndroidInjector
    abstract NewPostFragment newPostFragment();

}
