package com.example.heyao.activityanimation.module;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.heyao.activityanimation.annotation.ApplicationContext;
import com.example.heyao.activityanimation.annotation.DatabaseInfo;

import dagger.Module;
import dagger.Provides;

/**
 * Created by heyao on 2017/9/20.
 */

@Module
public class ApplicationModule {

    private Application mApplication;

    public ApplicationModule(Application application) {
        this.mApplication = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return mApplication;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return "demo-dagger.db";
    }

    @Provides
    @DatabaseInfo
    Integer provideDatabaseVersion() {
        return 2;
    }

    @Provides
    SharedPreferences provideSharedPrefs() {
        return mApplication.getSharedPreferences("demo-prefs", Context.MODE_PRIVATE);
    }
}
