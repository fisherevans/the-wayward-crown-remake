package com.fisherevans.twc.game.input;

import com.fisherevans.twc.game.TWCGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class InputListener implements KeyListener {
    private static final Logger log = LoggerFactory.getLogger(InputListener.class);

    private final TWCGame game;
    private Map<Integer, Key> keyMap;

    public InputListener(TWCGame game) {
        this.game = game;
        keyMap = Key.defaultKeyMap();
    }

    @Override
    public void keyPressed(int i, char c) {
        try {
            if (game.getCurrentState() != null) {
                game.getCurrentState().keyPressed(game, keyMap.get(i), c);
            }
        } catch (Exception e) {
            log.error("Failed to send keyPressed event: " + i + " " + c, e);
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        try {
            if (game.getCurrentState() != null) {
                game.getCurrentState().keyReleased(game, keyMap.get(i), c);
            }
        } catch (Exception e) {
            log.error("Failed to send keyReleased event: " + i + " " + c, e);
        }
    }

    @Override
    public void setInput(Input input) {

    }

    @Override
    public boolean isAcceptingInput() {
        return true;
    }

    @Override
    public void inputEnded() {

    }

    @Override
    public void inputStarted() {

    }
}
