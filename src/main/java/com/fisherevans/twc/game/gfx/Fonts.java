package com.fisherevans.twc.game.gfx;

import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public enum Fonts {
    DEFAULT_1("fonts/8bm1.fnt", "fonts/8bm1_0.png"),
    DEFAULT_2("fonts/8bm2.fnt", "fonts/8bm2_0.png"),
    DEFAULT_3("fonts/8bm3.fnt", "fonts/8bm3_0.png"),
    DEFAULT_4("fonts/8bm4.fnt", "fonts/8bm4_0.png"),
    DEFAULT_5("fonts/8bm5.fnt", "fonts/8bm5_0.png"),
    DEFAULT_6("fonts/8bm6.fnt", "fonts/8bm6_0.png"),
    DEFAULT_7("fonts/8bm7.fnt", "fonts/8bm7_0.png"),
    DEFAULT_8("fonts/8bm8.fnt", "fonts/8bm8_0.png"),
    MINI_NUMBERS("fonts/mini_numbers.fnt", "fonts/mini_numbers.png"),
    ;

    private final String fnt, png;

    Fonts(String fnt, String png) {
        this.fnt = fnt;
        this.png = png;
    }

    public AngelCodeFont load() throws SlickException {
        return new AngelCodeFont(fnt, new Image(png, false, Image.FILTER_NEAREST));
    }
}
