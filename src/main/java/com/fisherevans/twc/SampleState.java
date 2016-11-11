package com.fisherevans.twc;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

public class SampleState {
    private Image image;
    public SampleState() {
    }

    public void init() {
        try {
            image = new Image("media/backdrop/dumb.png", false, Image.FILTER_NEAREST);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void render(Graphics graphics) {
        graphics.drawImage(image, 0, 0);
    }
}
