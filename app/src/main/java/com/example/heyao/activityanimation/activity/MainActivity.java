package com.example.heyao.activityanimation.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.heyao.activityanimation.R;
import com.example.heyao.activityanimation.constant.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private String imageUrl = "https://img.vipsouq.net/yks-resource/screen/2017-07-14/20170714122131431_q2Qb_mid.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv_jump).setOnClickListener(this);
        ImageView iv = (ImageView) findViewById(R.id.iv);
        iv.setOnClickListener(this);
        Glide.with(this).load(imageUrl).into(iv);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(this, SecondActivity.class);
        switch (view.getId()) {
            case R.id.tv_jump:
                startActivity(intent);
                break;
            case R.id.iv:
                intent.putExtra(Constant.IMAGE_URL_EXTRA, imageUrl);
                intent.putExtra(Constant.VIEW_INFO_EXTRA, /* start values */ captureValues(view));
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
        }
    }

    private Bundle captureValues(@NonNull View view) {
        Bundle b = new Bundle();
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        b.putInt(Constant.PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
        b.putInt(Constant.PROPNAME_SCREENLOCATION_TOP, screenLocation[1]);
        b.putInt(Constant.PROPNAME_WIDTH, view.getWidth());
        b.putInt(Constant.PROPNAME_HEIGHT, view.getHeight());
        return b;
    }

    /**
     * Helper method to capture the view values to animate
     *
     * @param view target view
     * @return Bundle with the captured values
     */
//    private static Bundle captureValues(@NonNull View view) {
//        Bundle b = new Bundle();
//        captureScaleValues(b, view);
//        captureScreenLocationValues(b, view);
//        return b;
//    }


//    public static void startActivityFromImage(
//            @NonNull Context context,
//            @NonNull String imageUrl,
//            @NonNull View originView) {
//
//        //noinspection ConstantConditions
//        if (context == null || !(context instanceof Activity) || TextUtils.isEmpty(imageUrl) || originView == null) {
//            Log.e(TAG, "Invalid params");
//            return;
//        }
//
//        Intent intent = new Intent(context, SecondActivity.class);
//        intent.putExtra(Constant.IMAGE_URL_EXTRA, imageUrl);
//        intent.putExtra(Constant.VIEW_INFO_EXTRA, captureValues(originView));
//
//        context.startActivity(intent);
//        ((Activity) context).overridePendingTransition(0, 0);
//    }
}
