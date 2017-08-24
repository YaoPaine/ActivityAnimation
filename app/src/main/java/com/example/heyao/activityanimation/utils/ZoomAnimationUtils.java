package com.example.heyao.activityanimation.utils;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by heyao on 2017/8/24.
 */

public class ZoomAnimationUtils {

    private static final int ANIMATION_DURATION = 300;

    public static ZoomInfo getZoomInfo(@NonNull View view) {
        int[] screenLocation = new int[2];
        view.getLocationOnScreen(screenLocation);
        return new ZoomInfo(view.getWidth(), view.getHeight(), screenLocation[0], screenLocation[1]);
    }

    public static void startZoomUpAnim(final ZoomInfo preViewInfo,
                                       final View targetView,
                                       final Animator.AnimatorListener listener) {
        int startWidth = preViewInfo.getWidth();
        int startHeight = preViewInfo.getHeight();
        int endWidth = targetView.getWidth();
        int endHeight = targetView.getHeight();
        int[] screenLocation = new int[2];
        targetView.getLocationOnScreen(screenLocation);
        int endX = screenLocation[0];
        int endY = screenLocation[1];
        float startScaleX = (float) endWidth / startWidth;
        float startScaleY = (float) endHeight / startHeight;
        int translationX = preViewInfo.getScreenX() - endX;
        int translationY = preViewInfo.getScreenY() - endY;
        targetView.setPivotX(0);
        targetView.setPivotY(0);
        targetView.setTranslationX(translationX);
        targetView.setTranslationY(translationY);
        targetView.setScaleX(1 / startScaleX);
        targetView.setScaleY(1 / startScaleY);

        targetView.setVisibility(View.VISIBLE);
        ViewPropertyAnimator animator = targetView.animate();
        animator.setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(ANIMATION_DURATION)
                .scaleX(1f)
                .scaleY(1f)
                .translationX(0)
                .translationY(0);
        if (listener != null) {
            animator.setListener(listener);
        }
        animator.start();
    }

    public static void startZoomDownAnim(ZoomInfo preViewInfo, final View targetView, final Animator.AnimatorListener listener) {
        int endWidth = preViewInfo.getWidth();
        int endHeight = preViewInfo.getHeight();
        int startWidth = targetView.getWidth();
        int startHeight = targetView.getHeight();
        int endX = preViewInfo.getScreenX();
        int endY = preViewInfo.getScreenY();
        float endScaleX = (float) endWidth / startWidth;
        float endScaleY = (float) endHeight / startHeight;
        int[] screenLocation = new int[2];
        targetView.getLocationOnScreen(screenLocation);
        int startX = screenLocation[0];
        int startY = screenLocation[1];
        int translationX = endX - startX;
        int translationY = endY - startY;
        targetView.setPivotX(0);
        targetView.setPivotY(0);
        targetView.setVisibility(View.VISIBLE);
        ViewPropertyAnimator animator = targetView.animate();
        animator.setInterpolator(new AccelerateDecelerateInterpolator())
                .setDuration(ANIMATION_DURATION)
                .scaleX(endScaleX)
                .scaleY(endScaleY)
                .translationX(translationX)
                .translationY(translationY);
        if (listener != null) {
            animator.setListener(listener);
        }
        animator.start();
    }

    public static void startBackgroundAlphaAnim(final View targetView,
                                                final ColorDrawable color,
                                                int... values) {
        if (targetView == null) return;
        if (values == null || values.length == 0) {
            values = new int[]{0, 255};
        }
        ObjectAnimator bgAnim = ObjectAnimator
                .ofInt(color, "alpha", values);
        bgAnim.setDuration(ANIMATION_DURATION);
        bgAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                targetView.setBackgroundDrawable(color);
            }
        });
        bgAnim.start();
    }
}
