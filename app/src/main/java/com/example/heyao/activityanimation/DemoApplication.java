package com.example.heyao.activityanimation;

import android.app.Application;
import android.content.Context;

import com.example.heyao.activityanimation.component.ApplicationComponent;
import com.example.heyao.activityanimation.component.DaggerApplicationComponent;
import com.example.heyao.activityanimation.module.ApplicationModule;
import com.example.heyao.activityanimation.provides.DataManager;

import javax.inject.Inject;

/**
 * Created by heyao on 2017/9/20.
 */

public class DemoApplication extends Application {

    private ApplicationComponent mAppComponent;

    @Inject
    DataManager dataManager;

    public static DemoApplication get(Context context) {
        return (DemoApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mAppComponent = DaggerApplicationComponent.builder().applicationModule(new ApplicationModule(this)).build();
        mAppComponent.injectApplication(this);
    }

    public ApplicationComponent getComponent() {
        return mAppComponent;
    }
}
