package com.phyte.sanraphindustries.viso.car.app;

import android.app.Application;
import com.phyte.sanraphindustries.viso.car.di.components.AppComponent;
import com.classic.car.di.components.DaggerAppComponent;
import com.phyte.sanraphindustries.viso.car.di.modules.AppModule;
import com.phyte.sanraphindustries.viso.car.di.modules.DbModule;

/**
 *Application Name: CarAssistant
 * Package name: com.classic.car.app
 *
 * File Description: Car Assistant
 * Creator: Continue to write classics
 * Creation time：16/5/29, afternoon 1:53
 */
public class CarApplication extends Application {
    private AppComponent mAppComponent;

    @Override public void onCreate() {
        super.onCreate();
        mAppComponent = DaggerAppComponent.builder()
                                          .appModule(new AppModule(this))
                                          .dbModule(new DbModule())
                                          .build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
