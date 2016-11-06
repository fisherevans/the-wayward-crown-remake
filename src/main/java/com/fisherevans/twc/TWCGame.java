package com.fisherevans.twc;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TWCGame extends BasicGame {
    private static final Logger log = LoggerFactory.getLogger(TWCGame.class);

    private int degreeA = 0, degreeB = 0;

    public TWCGame(String title) {
        super(title);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        degreeA += i/2;
        degreeA = degreeA % 360;
        degreeB += i/3;
        degreeB = degreeB % 360;
    }

    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clear();
        graphics.setColor(Color.white);

        int xA = (int) (350 + 250*Math.sin((Math.toRadians(degreeA))));
        int yA = (int) (350 + 250*Math.cos((Math.toRadians(degreeA))));
        int xB = (int) (xA + 50*Math.sin((Math.toRadians(degreeB))));
        int yB = (int) (yA + 50*Math.cos((Math.toRadians(degreeB))));
        graphics.fillOval(xA-10, yA-10, 20, 20);
        graphics.fillOval(xB-3, yB-3, 6, 6);
    }
}
