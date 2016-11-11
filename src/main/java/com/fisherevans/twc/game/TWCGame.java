package com.fisherevans.twc.game;

import com.fisherevans.twc.game.gfx.RenderContext;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import org.newdawn.slick.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class TWCGame extends BasicGame {
    private static final Logger log = LoggerFactory.getLogger(TWCGame.class);

    private RenderContext renderContext;
    private TWCState currentState, nextState;
    private final Set<TWCState> initializedStates = new HashSet();
    private AngelCodeFont debugFont;

    public TWCGame(String title, RenderContext renderContext, TWCState initialState) {
        super(title);
        this.renderContext = renderContext;
        this.currentState = null;
        this.nextState = initialState;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        debugFont = Fonts.DEFAULT_1.load();
    }

    @Override
    public void update(GameContainer gameContainer, int deltaMillis) throws SlickException {
        if(nextState != null) {
            if(currentState != null) {
                currentState.leave(this);
            }
            currentState = nextState;
            nextState = null;
            if(!initializedStates.contains(currentState)) {
                currentState.init(this);
            }
            currentState.enter(this);
        }
        final float delta = ((float) deltaMillis)/1000f;
        currentState.update(this, delta);
    }

    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clear();
        graphics.scale(renderContext.actualScale, renderContext.actualScale);
        if(currentState != null) {
            currentState.render(this, graphics);
        }
        graphics.resetTransform();
        debugFont.drawString(10, 10, String.format("FPS:%d", gameContainer.getFPS()));
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }
}
