package com.fisherevans.twc.game.gfx.util;

import org.newdawn.slick.*;

public class GFX {
    /**
     * Draw an image to the display with the top left of the image at given x and y
     * @param x x pos of top left of the image
     * @param y y pos of the top left of the image
     * @param img The image to draw
     */
    public static void drawImage(float x, float y, Image img) {
        img.draw(x, y, img.getWidth(), img.getHeight());
    }

    public static void drawImage(float x, float y, Image img, Color color) {
        img.draw(x, y, color);
    }

    /**
     * Draw an image to the display with the top left of the image at given x and y and stretched to the given width and height
     * @param x x pos of top left of the image
     * @param y y pos of the top left of the image
     * @param width the width to draw the image
     * @param height thr height to draw the image
     * @param img The image to draw
     */
    public static void drawImage(float x, float y, float width, float height, Image img) {
        img.draw(x, y, width, height);
    }

    /**
     * Draw an image to the display centered on the given x and y
     * @param x the x pos to center the image on
     * @param y the y pos to center the image on
     * @param img the image to draw
     */
    public static void drawImageCentered(float x, float y, Image img) {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.draw(x+dx, y+dy, img.getWidth(), img.getHeight());
    }

    public static void drawImageCentered(float x, float y, float scale, Image img) {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.draw(x+dx, y+dy,scale);
    }

    public static void drawImageCentered(float x, float y, float width, float height, Image img) {
        float dx = -width/2f;
        float dy = -height/2f;
        img.draw(x+dx, y+dy, width, height);
    }

    public static void drawImageCentered(float x, float y, Image img, Color c) {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.draw(x+dx, y+dy, img.getWidth(), img.getHeight(), c);
    }

    public static void drawFlashImageCentered(float x, float y, Image img, Color c) {
        float dx = -img.getWidth()/2f;
        float dy = -img.getHeight()/2f;
        img.drawFlash(x+dx, y+dy, img.getWidth(), img.getHeight(), c);
    }

    /**
     * Draw an image to the display centered on the given x and y and scaled
     * @param x the x pos to center the image on
     * @param y the y pos to center the image on
     * @param img the image to draw
     */
    public static void drawImageCenteredRounded(float x, float y, Image img, int scale, Color c) {
        float dx = -img.getWidth()/2f*scale;
        float dy = -img.getHeight()/2f*scale;
        img.draw((int)(x+dx), (int)(y+dy), img.getWidth()*scale, img.getHeight()*scale, c);
    }

    public static float filterDrawPosition(float input, float scale) {
        return ((int)(input*scale))/scale;
    }
}
