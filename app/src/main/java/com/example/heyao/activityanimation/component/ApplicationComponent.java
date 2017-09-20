package com.example.heyao.activityanimation.component;

import android.app.Application;
import android.content.Context;

import com.example.heyao.activityanimation.DemoApplication;
import com.example.heyao.activityanimation.annotation.ApplicationContext;
import com.example.heyao.activityanimation.module.ApplicationModule;
import com.example.heyao.activityanimation.provides.DataManager;
import com.example.heyao.activityanimation.provides.DbHelper;
import com.example.heyao.activityanimation.provides.SharedPrefsHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by heyao on 2017/9/20.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void injectApplication(DemoApplication application);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    SharedPrefsHelper getPreferenceHelper();

    DbHelper getDbHelper();
}
