package com.example.heyao.activityanimation.module;

import com.example.heyao.activityanimation.dagger2.Cooker;

import dagger.Module;
import dagger.Provides;

/**
 * Created by heyao on 2017/9/19.
 */

@Module
public class SimpleModule {

    @Provides
    public String provideTitle() {
        return "provideTitle: " + getClass().getSimpleName();
    }

    @Provides
    public Cooker provideCooker() {
        return new Cooker("James", "Espresso");
    }

}
