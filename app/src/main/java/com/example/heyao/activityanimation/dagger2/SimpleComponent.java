package com.example.heyao.activityanimation.dagger2;

import com.example.heyao.activityanimation.activity.SecondActivity;

import dagger.Component;

/**
 * Created by heyao on 2017/9/19.
 */

@Component(modules = SimpleModule.class)
public interface SimpleComponent {

    void injectActivity(SecondActivity activity);
}
