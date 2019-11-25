package com.codedevtech.portfolioapp.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.codedevtech.portfolioapp.di.annotations.ViewModelKey;
import com.codedevtech.portfolioapp.di.models.FactoryViewModel;
import com.codedevtech.portfolioapp.fragments.OnboardingFragment;
import com.codedevtech.portfolioapp.viewmodels.CompleteProfileViewModel;
import com.codedevtech.portfolioapp.viewmodels.AuthenticationFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.OnboardingFragmentViewModel;
import com.codedevtech.portfolioapp.viewmodels.RegistrationFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthenticationFragmentViewModel.class)
    abstract ViewModel bindAuthenticationFragmentViewModel(AuthenticationFragmentViewModel authenticationFragmentViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(RegistrationFragmentViewModel.class)
    abstract ViewModel bindRegistrationFragmentViewModel(RegistrationFragmentViewModel registrationFragmentViewModel);


    @Binds
    @IntoMap
    @ViewModelKey(CompleteProfileViewModel.class)
    abstract ViewModel bindCompleteFragmentViewModel(CompleteProfileViewModel completeProfileViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DashboardFragmentViewModel.class)
    abstract ViewModel bindDashboardFragmentViewModel(DashboardFragmentViewModel dashboardFragmentViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(OnboardingFragmentViewModel.class)
    abstract ViewModel bindOnboardingFragmentViewModel(OnboardingFragmentViewModel onboardingFragmentViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelProviderFactory (FactoryViewModel factoryViewModel);


}
