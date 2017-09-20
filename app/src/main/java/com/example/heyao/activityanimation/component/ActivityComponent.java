package com.example.heyao.activityanimation.component;

import com.example.heyao.activityanimation.activity.SampleActivity;
import com.example.heyao.activityanimation.annotation.PerActivity;
import com.example.heyao.activityanimation.module.ActivityModule;

import dagger.Component;

/**
 * Created by heyao on 2017/9/20.
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(SampleActivity activity);

}
