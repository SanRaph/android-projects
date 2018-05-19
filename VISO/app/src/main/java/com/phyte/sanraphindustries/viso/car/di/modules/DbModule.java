/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.phyte.sanraphindustries.viso.car.di.modules;

import android.app.Application;
import android.database.sqlite.SQLiteOpenHelper;

import com.classic.car.BuildConfig;
import com.phyte.sanraphindustries.viso.car.db.DbOpenHelper;
import com.phyte.sanraphindustries.viso.car.db.dao.ConsumerDao;
import com.phyte.sanraphindustries.viso.car.utils.LogUtil;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

/**
 * Application Name: CarAssistant
 * Package Name: com.classic.car.di.modules
 *
 * File Description: Database-Related Instance Generation
 * Created by: Continued to write classic
 * Created: 16/6/5 2:07 PM
 */
@Module public final class DbModule {

    @Provides @Singleton SQLiteOpenHelper provideOpenHelper(Application application) {
        return new DbOpenHelper(application);
    }

    @SuppressWarnings("SpellCheckingInspection") @Provides @Singleton SqlBrite provideSqlBrite() {
        final SqlBrite.Builder builder = new SqlBrite.Builder();
        if (BuildConfig.DEBUG) {
            //noinspection CheckResult
            builder.logger(new SqlBrite.Logger() {
                @Override public void log(String message) {
                    LogUtil.d(message);
                }
            });
        }
        return builder.build();
    }

    @Provides @Singleton BriteDatabase provideDatabase(SqlBrite sqlBrite, SQLiteOpenHelper helper) {
        BriteDatabase db = sqlBrite.wrapDatabaseHelper(helper, Schedulers.io());
        db.setLoggingEnabled(BuildConfig.DEBUG);
        return db;
    }

    @Provides @Singleton ConsumerDao provideConsumerDao(BriteDatabase briteDatabase) {
        return new ConsumerDao(briteDatabase);
    }
}
