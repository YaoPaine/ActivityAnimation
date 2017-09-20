package com.example.heyao.activityanimation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.example.heyao.activityanimation.DemoApplication;
import com.example.heyao.activityanimation.R;
import com.example.heyao.activityanimation.component.ActivityComponent;
import com.example.heyao.activityanimation.component.DaggerActivityComponent;
import com.example.heyao.activityanimation.dagger2.User;
import com.example.heyao.activityanimation.module.ActivityModule;
import com.example.heyao.activityanimation.provides.DataManager;

import javax.inject.Inject;

/**
 * Created by heyao on 2017/9/20.
 */

public class SampleActivity extends AppCompatActivity {

    private static final String TAG = "SampleActivity";
    @Inject
    DataManager mDataManager;

    private ActivityComponent activityComponent;
    private TextView mTvUserInfo;
    private TextView mTvAccessToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);
        Log.e(TAG, "onCreate: ");

        getActivityComponent().inject(this);

        mTvUserInfo = (TextView) findViewById(R.id.tv_user_info);
        mTvAccessToken = (TextView) findViewById(R.id.tv_access_token);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Log.e(TAG, "onPostCreate: ");
        createUser();
        getUser();
        mDataManager.saveAccessToken("ASDR12443JFDJF43543J543H3K543");

        String token = mDataManager.getAccessToken();
        if (token != null) {
            mTvAccessToken.setText(token);
        }
    }

    public ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = DaggerActivityComponent.builder()
                    .activityModule(new ActivityModule(this))
                    .applicationComponent(DemoApplication.get(this).getComponent())
                    .build();
        }
        return activityComponent;
    }

    private void createUser() {
        try {
            mDataManager.createUser(new User("Ali", "1367, Gurgaon, Haryana, India"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getUser() {
        try {
            User user = mDataManager.getUser(1L);
            mTvUserInfo.setText(user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
