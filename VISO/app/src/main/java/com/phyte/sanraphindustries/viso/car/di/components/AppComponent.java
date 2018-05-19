package com.phyte.sanraphindustries.viso.car.di.components;

import com.phyte.sanraphindustries.viso.car.di.modules.AppModule;
import com.phyte.sanraphindustries.viso.car.ui.activity.AddConsumerActivity;
import com.phyte.sanraphindustries.viso.car.ui.activity.ChartActivity;
import com.phyte.sanraphindustries.viso.car.ui.fragment.AboutFragment;
import com.phyte.sanraphindustries.viso.car.ui.fragment.ChartFragment;
import com.phyte.sanraphindustries.viso.car.ui.fragment.MainFragment;
import com.phyte.sanraphindustries.viso.car.ui.fragment.TimelineFragment;

import javax.inject.Singleton;

import dagger.Component;


/**
 * Application Name: CarAssistant
 * Package name: com.classic.car.adi.component
 *
 * File Description: Car Assistant
 * Creator: Continue to write classics
 * Creation time：16/5/29, afternoon 02:07
 */
@Singleton @Component(modules = {AppModule.class}) public interface AppComponent {
    void inject(AddConsumerActivity activity);

    void inject(ChartActivity activity);

    void inject(MainFragment fragment);

    void inject(TimelineFragment fragment);

    void inject(ChartFragment fragment);

    void inject(AboutFragment fragment);
}
