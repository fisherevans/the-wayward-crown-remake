package com.fisherevans.twc;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.states.splash.SplashState;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    private final RenderContext renderContext;
    private final boolean fullscreen;
    private final TWCGame game;
    private final AppGameContainer gameContainer;

    private Launcher(RenderContext renderContext, boolean fullscreen) throws SlickException {
        this.renderContext = renderContext;
        this.fullscreen = fullscreen;
        game = new TWCGame("TWC", renderContext, new SplashState());
        gameContainer = new AppGameContainer(game);
        gameContainer.setDisplayMode(
                (int) renderContext.getActualWidth(),
                (int) renderContext.getActualHeight(),
                fullscreen);
        gameContainer.setUpdateOnlyWhenVisible(false);
        gameContainer.setAlwaysRender(true);
        gameContainer.setShowFPS(false);
        gameContainer.setVSync(true);
        gameContainer.setTargetFrameRate(60);
    }

    public void start() throws SlickException {
        gameContainer.start();
    }

    public static void main(String[] args) throws SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("target/natives").getAbsolutePath());
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "The Wayward Crown");
        (new Launcher(new RenderContext(2), false)).start();
    }
}
