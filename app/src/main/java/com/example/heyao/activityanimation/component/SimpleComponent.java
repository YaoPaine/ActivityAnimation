package com.example.heyao.activityanimation.component;

import com.example.heyao.activityanimation.activity.SecondActivity;
import com.example.heyao.activityanimation.module.SimpleModule;

import dagger.Component;

/**
 * Created by heyao on 2017/9/19.
 */

@Component(modules = SimpleModule.class)
public interface SimpleComponent {

    void injectActivity(SecondActivity activity);
}
