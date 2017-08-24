package com.example.heyao.activityanimation.utils;

/**
 * Created by heyao on 2017/8/24.
 */

public class ZoomInfo {
    private int width;
    private int height;
    private int screenX;
    private int screenY;

    public ZoomInfo(int width, int height, int screenX, int screenY) {
        this.width = width;
        this.height = height;
        this.screenX = screenX;
        this.screenY = screenY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getScreenX() {
        return screenX;
    }

    public void setScreenX(int screenX) {
        this.screenX = screenX;
    }

    public int getScreenY() {
        return screenY;
    }

    public void setScreenY(int screenY) {
        this.screenY = screenY;
    }
}
