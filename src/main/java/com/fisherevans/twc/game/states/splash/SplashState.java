package com.fisherevans.twc.game.states.splash;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.Fonts;
import com.fisherevans.twc.game.gfx.Text;
import org.newdawn.slick.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SplashState implements TWCState {
    private static final Logger log = LoggerFactory.getLogger(SplashState.class);

    private float degrees = 0f;

    private AngelCodeFont bigFont, smallFont;
    private Text mainText;

    @Override
    public void init(TWCGame game) throws SlickException {
        bigFont = Fonts.DEFAULT_4.load();
        smallFont = Fonts.MINI_NUMBERS.load();
        int width = 400;
        mainText = new Text.Builder(bigFont, "The Wayward Crown")
                .aligned(Text.AlignHorz.CENTER, Text.AlignVert.MIDDLE)
                .topLeftPosition(game.getRenderContext().width/2f - width/2f, game.getRenderContext().height/2f)
                .size(width, 0)
                .wrapped(true)
                .lineHeight(1.15f)
                .build();
    }

    @Override
    public void enter(TWCGame game) throws SlickException {

    }

    @Override
    public void update(TWCGame game, float deltaSeconds) throws SlickException {
        degrees = (degrees + deltaSeconds * 180) % 360;
    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        final float brightness = (float) (Math.sin(Math.toRadians(degrees)) + 1f)/4f + 0.5f;
        final Color color = new Color(brightness, brightness, brightness);
        mainText.draw(color);
        smallFont.drawString(10, game.getRenderContext().height-30, String.format("%.5f", brightness));
    }

    @Override
    public void leave(TWCGame game) throws SlickException {

    }
}
