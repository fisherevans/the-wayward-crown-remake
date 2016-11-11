package com.fisherevans.twc.game.gfx;

public class RenderContext {
    private static final float BASE_WIDTH = 480;
    private static final float BASE_HEIGHT = 270;

    public final float width, height;
    public final float actualScale;

    public RenderContext(float width, float height, float actualScale) {
        this.width = width;
        this.height = height;
        this.actualScale = actualScale;
    }

    public RenderContext(float actualScale) {
        this(BASE_WIDTH, BASE_HEIGHT, actualScale);
    }

    public float getActualWidth() {
        return width * actualScale;
    }

    public float getActualHeight() {
        return height * actualScale;
    }
}
