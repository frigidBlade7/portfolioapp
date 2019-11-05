package com.codedevtech.portfolioapp.di.modules;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.codedevtech.portfolioapp.di.annotations.ViewModelKey;
import com.codedevtech.portfolioapp.di.models.FactoryViewModel;
import com.codedevtech.portfolioapp.viewmodels.DashboardFragmentViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(DashboardFragmentViewModel.class)
    abstract ViewModel bindDashboardFragmentViewModel(DashboardFragmentViewModel dashboardFragmentViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelProviderFactory (FactoryViewModel factoryViewModel);
}
