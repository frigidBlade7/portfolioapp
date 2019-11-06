package com.codedevtech.portfolioapp.di.interfaces;


import android.app.Application;

import com.codedevtech.portfolioapp.PortfolioApp;
import com.codedevtech.portfolioapp.di.modules.ActivityModule;
import com.codedevtech.portfolioapp.di.modules.AppModule;
import com.codedevtech.portfolioapp.di.modules.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        AndroidSupportInjectionModule.class,
        ActivityModule.class,
        FragmentModule.class,
        AppModule.class
})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application (Application application);

        AppComponent build();
    }

    void inject(PortfolioApp portfolioApp);
}
