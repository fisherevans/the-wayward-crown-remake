package com.fisherevans.twc.game;

import com.fisherevans.twc.game.gfx.RenderContext;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import com.fisherevans.twc.game.input.InputListener;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TWCGame extends BasicGame {
    private static final Logger log = LoggerFactory.getLogger(TWCGame.class);

    private RenderContext renderContext;
    private TWCState currentState;

    private InputListener inputListener;

    private AngelCodeFont debugFont;

    public TWCGame(String title, RenderContext renderContext, TWCState initialState) {
        super(title);
        this.renderContext = renderContext;
        this.currentState = initialState;
        inputListener = new InputListener(this);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
        debugFont = Fonts.DEFAULT_1.load();
        gameContainer.getInput().addKeyListener(inputListener);
        if(currentState != null) {
            currentState.init(this);
            currentState.enter(this);
        }
    }

    @Override
    public void update(GameContainer gameContainer, int deltaMillis) throws SlickException {
        final float delta = ((float) deltaMillis)/1000f;
        currentState.update(this, delta);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        graphics.clear();
        graphics.scale(renderContext.actualScale, renderContext.actualScale);
        if(currentState != null) {
            currentState.render(this, graphics);
        }
        graphics.resetTransform();
        debugFont.drawString(4, 4, String.format("FPS:%d", gameContainer.getFPS()));
    }

    public RenderContext getRenderContext() {
        return renderContext;
    }

    public TWCState getCurrentState() {
        return currentState;
    }

    public void setNextState(TWCState nextState) throws SlickException {
        if(this.currentState != null) {
            this.currentState.leave(this);
        }
        this.currentState = nextState;
        this.currentState.enter(this);
    }
}
