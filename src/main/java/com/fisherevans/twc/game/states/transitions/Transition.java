package com.fisherevans.twc.game.states.transitions;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.RenderContext;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

public interface Transition {
    void render(TWCState previous, TWCState next, TWCGame game, Graphics graphics, float position) throws SlickException;

    static Transition simpleFade() {
        return ((previous, next, game, graphics, position) -> {
            Color color;
            if(position < 0.5) {
                previous.render(game, graphics);
                color = new Color(0, 0, 0, position*2f);
            } else {
                next.render(game, graphics);
                color = new Color(0, 0, 0, 1f - ((position - 0.5f)*2f));
            }
            graphics.setColor(color);
            graphics.fillRect(0, 0, game.getRenderContext().width, game.getRenderContext().height);
        });
    }
}
