package com.fisherevans.twc.game;

import com.fisherevans.twc.game.input.Key;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface TWCState {
    void init(TWCGame game) throws SlickException;

    void enter(TWCGame game) throws SlickException;

    void update(TWCGame game, float deltaSeconds) throws SlickException;

    void render(TWCGame game, Graphics graphics) throws SlickException;

    void keyPressed(TWCGame game, Key key, char c);

    void keyReleased(TWCGame game, Key key, char c);

    void leave(TWCGame game) throws SlickException;
}
