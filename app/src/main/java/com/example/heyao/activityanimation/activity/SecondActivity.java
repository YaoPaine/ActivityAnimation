package com.example.heyao.activityanimation.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.heyao.activityanimation.R;
import com.example.heyao.activityanimation.component.DaggerSimpleComponent;
import com.example.heyao.activityanimation.constant.Constant;
import com.example.heyao.activityanimation.dagger2.Cooker;
import com.example.heyao.activityanimation.utils.ImageViewUtil;

import javax.inject.Inject;

public class SecondActivity extends AppCompatActivity implements ViewTreeObserver.OnPreDrawListener {

    private static final String TAG = "SecondActivity";

    private ImageView mDestinationView;
    private FrameLayout mImageContainer;
    private static final AccelerateDecelerateInterpolator DEFAULT_INTERPOLATOR = new AccelerateDecelerateInterpolator();
    private static final int DEFAULT_DURATION = 300;
    // Bundle that will contain the transition start values
    private Bundle mStartValues;
    // Bundle that will contain the transition end values
    final private Bundle mEndValues = new Bundle();

    //    @Inject
//    CoffeeMachine coffeeMachine;
    @Inject
    Cooker cooker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.e(TAG, "onCreate: ");
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(Constant.IMAGE_URL_EXTRA);
        mStartValues = intent.getBundleExtra(Constant.VIEW_INFO_EXTRA);

        mImageContainer = (FrameLayout) findViewById(R.id.image_container);
        mDestinationView = (ImageView) findViewById(R.id.iv_second_activity);
        if (mDestinationView != null) {
            mDestinationView.setVisibility(View.INVISIBLE);
        }

        Glide.with(this).load(imageUrl).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                Toast.makeText(getApplicationContext(), "图片加载完成了", Toast.LENGTH_SHORT).show();
                mDestinationView.getViewTreeObserver().addOnPreDrawListener(SecondActivity.this);
                return false;
            }
        }).into(mDestinationView);

        DaggerSimpleComponent.create().injectActivity(this);

        Log.e(TAG, "onCreate: " + cooker.make());
    }

    @Override
    public boolean onPreDraw() {
        // remove previous listener
        mDestinationView.getViewTreeObserver().removeOnPreDrawListener(this);
        // prep the scene
        prepareScene();
        // run the animation
        runEnterAnimation();
        return true;
    }

    private void runEnterAnimation() {
        mDestinationView.setVisibility(View.VISIBLE);
        mImageContainer.setBackgroundResource(R.color.background);
        mDestinationView.animate()
                .setDuration(DEFAULT_DURATION)
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .scaleX(1.0f)
                .scaleY(1.0f)
                .translationY(0)
                .translationX(0)
                .start();
    }

    private void prepareScene() {
        // do the first capture to scale the image
        captureScaleValues2(mEndValues, mDestinationView);

        // calculate the scale factors
        float scaleX = scaleDelta(mStartValues, mEndValues, Constant.PROPNAME_WIDTH);
        float scaleY = scaleDelta(mStartValues, mEndValues, Constant.PROPNAME_HEIGHT);

        // scale the image
        mDestinationView.setScaleX(scaleX);
        mDestinationView.setScaleY(scaleY);

        // as scaling the image will change the top and left coordinates, we need to re-capture
        // the values to proper figure out the translation deltas w.r.t. to start view
        captureScreenLocationValues(mEndValues, mDestinationView);

        int deltaX = translationDelta(mStartValues, mEndValues, Constant.PROPNAME_SCREENLOCATION_LEFT);
        int deltaY = translationDelta(mStartValues, mEndValues, Constant.PROPNAME_SCREENLOCATION_TOP);
        // finally, translate the end view to where the start view was
        mDestinationView.setTranslationX(deltaX);
        mDestinationView.setTranslationY(deltaY);
    }

    private void captureScaleValues(@NonNull Bundle bundle, @NonNull View view) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        bundle.putInt(Constant.PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
        bundle.putInt(Constant.PROPNAME_SCREENLOCATION_TOP, screenLocation[1]);
        bundle.putInt(Constant.PROPNAME_WIDTH, view.getWidth());
        bundle.putInt(Constant.PROPNAME_HEIGHT, view.getHeight());
    }

    private static void captureScaleValues2(@NonNull Bundle b, @NonNull View view) {
        if (view instanceof ImageView) {
            int[] size = ImageViewUtil.getDisplayedImageLocation((ImageView) view);
            b.putInt(Constant.PROPNAME_WIDTH, size[2]);
            b.putInt(Constant.PROPNAME_HEIGHT, size[3]);
        } else {
            b.putInt(Constant.PROPNAME_WIDTH, view.getWidth());
            b.putInt(Constant.PROPNAME_HEIGHT, view.getHeight());
        }
    }

    private static void captureScreenLocationValues(@NonNull Bundle b, @NonNull View view) {
        if (view instanceof ImageView) {
            int[] size = ImageViewUtil.getDisplayedImageLocation((ImageView) view);
            b.putInt(Constant.PROPNAME_SCREENLOCATION_LEFT, size[0]);
            b.putInt(Constant.PROPNAME_SCREENLOCATION_TOP, size[1]);
        } else {
            int[] screenLocation = new int[2];
            view.getLocationOnScreen(screenLocation);
            b.putInt(Constant.PROPNAME_SCREENLOCATION_LEFT, screenLocation[0]);
            b.putInt(Constant.PROPNAME_SCREENLOCATION_TOP, screenLocation[1]);
        }
    }

    /**
     * Helper method to calculate the scale delta given start and end values
     *
     * @param startValues  start values {@link Bundle}
     * @param endValues    end values {@link Bundle}
     * @param propertyName property name
     * @return scale delta value
     */
    private float scaleDelta(@NonNull Bundle startValues, @NonNull Bundle endValues, @NonNull String propertyName) {
        int startValue = startValues.getInt(propertyName);
        int endValue = endValues.getInt(propertyName);
        float delta = (float) startValue / endValue;
//        Log.e(TAG, String.format(Locale.US, "%s: startValue = %d, endValue = %d, delta = %f", propertyName, startValue, endValue, delta));
        return delta;
    }

    /**
     * Helper method to calculate the translation deltas given start and end values
     *
     * @param startValues  start values {@link Bundle}
     * @param endValues    end values {@link Bundle}
     * @param propertyName property name
     * @return translation delta between start and end values
     */
    private int translationDelta(@NonNull Bundle startValues, @NonNull Bundle endValues, @NonNull String propertyName) {
        int startValue = startValues.getInt(propertyName);
        int endValue = endValues.getInt(propertyName);
        int delta = startValue - endValue;
//        Log.e(TAG, String.format(Locale.US, "%s: startValue = %d, endValue = %d, delta = %d", propertyName, startValue, endValue, delta));
        return delta;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        runExitAnimation();
    }

    /**
     * Call this method to run the exit transition
     */
    private void runExitAnimation() {
        // re-calculate deltas
        int deltaX = translationDelta(mStartValues, mEndValues, Constant.PROPNAME_SCREENLOCATION_LEFT);
        int deltaY = translationDelta(mStartValues, mEndValues, Constant.PROPNAME_SCREENLOCATION_TOP);
        float scaleX = scaleDelta(mStartValues, mEndValues, Constant.PROPNAME_WIDTH);
        float scaleY = scaleDelta(mStartValues, mEndValues, Constant.PROPNAME_HEIGHT);

        mDestinationView.animate()
                .setDuration(DEFAULT_DURATION)
                .setInterpolator(DEFAULT_INTERPOLATOR)
                .scaleX(scaleX)
                .scaleY(scaleY)
                .translationX(deltaX)
                .translationY(deltaY)
                .withEndAction(() -> {
                    finish();
                    overridePendingTransition(0, 0);
                }).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.e(TAG, "onRestart: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        overridePendingTransition(0, 0);
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    public void onClick(View view) {

    }
}
