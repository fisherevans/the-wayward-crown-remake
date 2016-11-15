package com.fisherevans.twc.util;

public class MathUtil {
    public static final float PI = (float) Math.PI;

    public static float clamp(float bottom, float x, float top) {
        return x < bottom ? bottom : x > top ? top : x;
    }

    public static float random() {
        return (float) Math.random();
    }

    public static float random(float low, float high) {
        return low + (random()*(high-low));
    }

    public static int randomInt(int low, int high) {
        return low + (int) (random()*(high-low));
    }

    public static float sin(float x) {
        return (float) Math.sin(x);
    }

    public static float cos(float x) {
        return (float) Math.cos(x);
    }

    public static float tan(float x) {
        return (float) Math.tan(x);
    }

    public static float atan(float x) {
        return (float) Math.atan(x);
    }
}
