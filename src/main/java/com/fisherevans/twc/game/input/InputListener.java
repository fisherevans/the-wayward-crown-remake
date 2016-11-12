package com.fisherevans.twc.game.input;

import com.fisherevans.twc.game.TWCGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import java.util.Map;

public class InputListener implements KeyListener {
    private final TWCGame game;
    private Map<Integer, Key> keyMap;

    public InputListener(TWCGame game) {
        this.game = game;
        keyMap = Key.defaultKeyMap();
    }

    @Override
    public void keyPressed(int i, char c) {
        if(game.getCurrentState() != null) {
            game.getCurrentState().keyPressed(game, keyMap.get(i), c);
        }
    }

    @Override
    public void keyReleased(int i, char c) {
        if(game.getCurrentState() != null) {
            game.getCurrentState().keyReleased(game, keyMap.get(i), c);
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
