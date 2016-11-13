package com.fisherevans.twc;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.gfx.RenderContext;
import com.fisherevans.twc.game.states.splash.SplashState;
import com.fisherevans.twc.slick.TWCLogSystem;
import com.fisherevans.twc.util.JavaUtil;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
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

    private static void loadLibraries() throws IOException {
        String natives = JavaUtil.createTempFolderWithJarFiles(new String[] {
                "/jinput-dx8.dll",     "/jinput-dx8_64.dll",    "/jinput-raw.dll",       "/jinput-raw_64.dll",         "/jinput-wintab.dll",
                "/liblwjgl.dylib",     "/liblwjgl.so",          "/liblwjgl64.so",        "/lwjgl.dll", "/lwjgl64.dll",
                "/libjinput-linux.so", "/libjinput-linux64.so", "/libjinput-osx.jnilib",
                "/libopenal.so",       "/libopenal64.so",
                "/OpenAL32.dll",       "/OpenAL64.dll",
                "/openal.dylib"
        }).toString();
        log.info("Libraries copied to: " + natives);
        System.setProperty("org.lwjgl.librarypath", natives);
        JavaUtil.addClassLoaderUserPath(natives);
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

    public static void main(String[] args) throws Exception {
        loadLibraries();
        redirectLogs();
        final Launcher launcher = new Launcher(new RenderContext(2), false);
        launcher.start();
    }
}
