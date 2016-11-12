package com.fisherevans.twc;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.gfx.RenderContext;
import com.fisherevans.twc.game.states.splash.SplashState;
import com.fisherevans.twc.slick.TWCLogSystem;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.logging.Level;

public class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    private final RenderContext renderContext;
    private final boolean fullscreen;
    private final TWCGame game;
    private final AppGameContainer gameContainer;

    private Launcher(RenderContext renderContext, boolean fullscreen) throws SlickException {
        this.renderContext = renderContext;
        this.fullscreen = fullscreen;
        game = new TWCGame("The Wayward Crown", renderContext, new SplashState());
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

    public static void main(String[] args) throws Exception {
        final String natives = new File("target/natives").getAbsolutePath();
        System.setProperty("org.lwjgl.librarypath", natives);
        addLibraryPath(natives);
        redirectLogs();
        final Launcher launcher = new Launcher(new RenderContext(3), false);
        launcher.start();
    }

    public static void addLibraryPath(String folderPath) throws IOException {
        try {
            Field field = ClassLoader.class.getDeclaredField("usr_paths");
            field.setAccessible(true);
            String[] paths = (String[])field.get(null);
            for (int i = 0; i < paths.length; i++) {
                if (folderPath.equals(paths[i])) {
                    return;
                }
            }
            String[] tmp = new String[paths.length+1];
            System.arraycopy(paths,0,tmp,0,paths.length);
            tmp[paths.length] = folderPath;
            field.set(null,tmp);
            System.setProperty("java.library.path", System.getProperty("java.library.path") + File.pathSeparator + folderPath);
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to get permissions to set library path");
        } catch (NoSuchFieldException e) {
            throw new IOException("Failed to get field handle to set library path");
        }
    }

    private static void redirectLogs() {
        // java.util.logging -> logback
        java.util.logging.LogManager.getLogManager().reset();
        org.slf4j.bridge.SLF4JBridgeHandler.removeHandlersForRootLogger();
        org.slf4j.bridge.SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);
        // slick2d logger
        Log.setLogSystem(new TWCLogSystem());
    }
}
