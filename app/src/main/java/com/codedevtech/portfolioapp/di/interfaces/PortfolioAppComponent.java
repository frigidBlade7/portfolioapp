package com.codedevtech.portfolioapp.di.interfaces;


import android.app.Application;

import com.codedevtech.portfolioapp.PortfolioApp;
import com.codedevtech.portfolioapp.di.modules.ActivityModule;
import com.codedevtech.portfolioapp.di.modules.FragmentModule;
import com.codedevtech.portfolioapp.di.modules.ViewModelModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        ViewModelModule.class
})
public interface PortfolioAppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application (Application application);

        PortfolioAppComponent build();
    }

    void inject(PortfolioApp portfolioApp);
}
