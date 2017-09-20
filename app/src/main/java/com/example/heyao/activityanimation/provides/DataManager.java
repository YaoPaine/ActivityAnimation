package com.example.heyao.activityanimation.provides;

import android.content.Context;

import com.example.heyao.activityanimation.annotation.ApplicationContext;
import com.example.heyao.activityanimation.dagger2.User;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by heyao on 2017/9/20.
 */

@Singleton
public class DataManager {
    private Context mContext;
    private DbHelper mDbHelper;
    private SharedPrefsHelper mSharedPrefsHelper;

    @Inject
    public DataManager(@ApplicationContext Context context, DbHelper dbHelper, SharedPrefsHelper sharedPrefsHelper) {
        this.mContext = context;
        this.mDbHelper = dbHelper;
        this.mSharedPrefsHelper = sharedPrefsHelper;
    }

    public void saveAccessToken(String accessToken) {
        mSharedPrefsHelper.put(SharedPrefsHelper.PREF_KEY_ACCESS_TOKEN, accessToken);
    }

    public String getAccessToken() {
        return mSharedPrefsHelper.get(SharedPrefsHelper.PREF_KEY_ACCESS_TOKEN, null);
    }

    public Long createUser(User user) throws Exception {
        return mDbHelper.insertUser(user);
    }

    public User getUser(Long userId) {
        return mDbHelper.getUser(userId);
    }
}
