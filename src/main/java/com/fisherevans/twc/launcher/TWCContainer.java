package com.fisherevans.twc.launcher;

import com.fisherevans.twc.RenderContext;
import com.fisherevans.twc.TWCGame;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TWCContainer {
    private static final Logger log = LoggerFactory.getLogger(TWCContainer.class);

    private final RenderContext renderContext;
    private final boolean fullscreen;
    private final TWCGame game;
    private final AppGameContainer gameContainer;

    private TWCContainer(RenderContext renderContext, boolean fullscreen) throws SlickException {
        this.renderContext = renderContext;
        this.fullscreen = fullscreen;

        game = new TWCGame("TWC");

        gameContainer = new AppGameContainer(game);
        gameContainer.setDisplayMode(renderContext.getActualWidth(), renderContext.getActualHeight(), fullscreen);
        gameContainer.setUpdateOnlyWhenVisible(false);
        gameContainer.setAlwaysRender(true);
        gameContainer.setShowFPS(false);
        //gameContainer.setMouseGrabbed(true);
        //gameContainer.setIcon("res/img/icon.png");
        gameContainer.setVSync(true);
        gameContainer.setTargetFrameRate(60);

    }

    public void start() throws SlickException {
        gameContainer.start();
    }

    public static void main(String[] args) throws SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("target/natives").getAbsolutePath());
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "The Wayward Crown");
        (new TWCContainer(new RenderContext(1), false)).start();
    }
}
