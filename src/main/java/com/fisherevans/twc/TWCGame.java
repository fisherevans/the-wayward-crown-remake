package com.fisherevans.twc;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TWCGame extends BasicGame {
    private static final Logger log = LoggerFactory.getLogger(TWCGame.class);

    private final SampleState sampleState;

    public TWCGame(String title) {
        super(title);
        sampleState = new SampleState();
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        sampleState.init();
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clear();

        int scaleX = gameContainer.getWidth()/480;
        int scaleY = gameContainer.getHeight()/270;
        graphics.scale(scaleX, scaleY);
        sampleState.render(graphics);
    }
}
