package com.fisherevans.twc;

import org.lwjgl.Sys;
import org.newdawn.slick.CanvasGameContainer;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

public class Launcher {
    private static final Logger log = LoggerFactory.getLogger(Launcher.class);

    public static void main(String[] args) throws SlickException {
        System.setProperty("org.lwjgl.librarypath", new File("target/natives").getAbsolutePath());
        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "The Wayward Crown");

        final TWCGame game = new TWCGame("TWC");

        final CanvasGameContainer gameCanvas = new CanvasGameContainer(game);
        gameCanvas.setBackground(Color.black);
        gameCanvas.setPreferredSize(new Dimension(800, 600));

        gameCanvas.getContainer().setAlwaysRender(true);
        gameCanvas.getContainer().setUpdateOnlyWhenVisible(false);
        gameCanvas.getContainer().setShowFPS(true);
        gameCanvas.getContainer().setClearEachFrame(false);
        gameCanvas.getContainer().setTargetFrameRate(60);
        gameCanvas.getContainer().setVSync(true);

        final GameContainer window = new GameContainer();
        window.setTitle("TWC");
        window.setBackground(Color.black);
        window.add(gameCanvas);
        window.pack();
        window.addComponentListener(window);
        window.addWindowListener(window);
        window.setVisible(true);

        gameCanvas.addComponentListener(window);
        window.center();
        gameCanvas.start();

        //window.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private static class GameContainer extends JFrame implements ComponentListener, WindowListener {
        public GameContainer() throws HeadlessException {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        public void center() {
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (dim.width-getSize().width)/2;
            int y = (dim.height-getSize().height)/2;
            setLocation(x, y);
        }

        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }

        public void componentResized(ComponentEvent e) { }
        public void componentMoved(ComponentEvent e) { }
        public void componentShown(ComponentEvent e) { }
        public void componentHidden(ComponentEvent e) { }
        public void windowOpened(WindowEvent e) { }
        public void windowClosed(WindowEvent e) { }
        public void windowIconified(WindowEvent e) { }
        public void windowDeiconified(WindowEvent e) { }
        public void windowActivated(WindowEvent e) { }
        public void windowDeactivated(WindowEvent e) { }
    }
}
