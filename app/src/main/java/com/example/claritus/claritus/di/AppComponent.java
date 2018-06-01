package com.example.claritus.claritus.di;

import android.app.Application;

import com.example.claritus.claritus.ClaritusApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        ActivityBuilderModule.class,
        AndroidInjectionModule.class,
        AppModule.class
})
public interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }
    void inject(ClaritusApp claritusApp);
}
