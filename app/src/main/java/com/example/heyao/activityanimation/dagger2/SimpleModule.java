package com.example.heyao.activityanimation.dagger2;

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
