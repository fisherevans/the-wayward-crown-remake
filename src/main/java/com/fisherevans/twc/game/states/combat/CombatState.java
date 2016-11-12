package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.input.Key;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public class CombatState implements TWCState {
    @Override
    public void init(TWCGame game) throws SlickException {

    }

    @Override
    public void enter(TWCGame game) throws SlickException {

    }

    @Override
    public void update(TWCGame game, float deltaSeconds) throws SlickException {

    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        graphics.setColor(Color.cyan);
        graphics.fillOval(0, 0, game.getRenderContext().width, game.getRenderContext().height, 8);
    }

    @Override
    public void keyPressed(TWCGame game, Key key, char c) {

    }

    @Override
    public void keyReleased(TWCGame game, Key key, char c) {

    }

    @Override
    public void leave(TWCGame game) throws SlickException {

    }
}
