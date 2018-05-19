package com.phyte.sanraphindustries.viso.car.di.modules;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.di.modules
 * File Description: Instance Generation
 * Created by: Continued to write classic
 * Created: 16/6/5 2:07 PM
 */
@Module(includes = { DbModule.class})
public class AppModule {

    private final Application mApplication;

    public AppModule(Application application) {
        this.mApplication = application;
    }

    @Provides @Singleton Application provideApplication() {
        return mApplication;
    }
}
