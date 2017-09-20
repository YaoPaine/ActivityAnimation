package com.example.heyao.activityanimation.module;

import android.app.Activity;
import android.content.Context;

import com.example.heyao.activityanimation.annotation.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by heyao on 2017/9/20.
 */

@Module
public class ActivityModule {

    private Activity mActivity;

    public ActivityModule(Activity activity) {
        mActivity = activity;
    }

    @Provides
    @PerActivity
    Context provideContext() {
        return mActivity;
    }

    @Provides
    Activity provideActivity() {
        return mActivity;
    }


}
