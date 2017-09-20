package com.example.heyao.activityanimation.component;

import com.example.heyao.activityanimation.DemoApplication;
import com.example.heyao.activityanimation.module.ApplicationModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by heyao on 2017/9/20.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void injectApplication(DemoApplication application);
}
