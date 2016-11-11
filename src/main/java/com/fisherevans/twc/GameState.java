package com.fisherevans.twc;

import org.newdawn.slick.Graphics;

public interface GameState {
    void init(TWCGame game);
    void enter();
    void update(float deltaSeconds);
    void render(RenderContext renderContext, Graphics graphics);
    void leave();
}
