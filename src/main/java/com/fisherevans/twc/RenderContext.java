package com.fisherevans.twc;

public class RenderContext {
    private static final int BASE_WIDTH = 480;
    private static final int BASE_HEIGHT = 270;

    public final int width, height;
    public final int actualScale;

    public RenderContext(int width, int height, int actualScale) {
        this.width = width;
        this.height = height;
        this.actualScale = actualScale;
    }

    public RenderContext(int actualScale) {
        this(BASE_WIDTH, BASE_HEIGHT, actualScale);
    }

    public int getActualWidth() {
        return width * actualScale;
    }

    public int getActualHeight() {
        return height * actualScale;
    }
}
