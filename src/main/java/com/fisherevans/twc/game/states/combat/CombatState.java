package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import com.fisherevans.twc.game.input.Key;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class CombatState implements TWCState {
    private static final Logger log = LoggerFactory.getLogger(CombatState.class);

    private enum ControlState { PLAYER, CPU , WAIT }

    private int unitDisplayScale = 16;
    private float unitTimeScale = 4;


    private List<Action> playerActionStack, cpuActionStack;
    private Action playerNext = null;
    private int playerCombo = 0;
    private int maxCombo = 0;
    private float unitsElapsed = 0f;
    private ControlState state = ControlState.WAIT;

    private AngelCodeFont playerFont, smallFont;

    public CombatState() {
        playerActionStack = new ArrayList();
        playerActionStack.add(new Action(1, "1 long"));

        cpuActionStack = new ArrayList();
        cpuActionStack.add(new Action(2, "2 long"));
    }

    @Override
    public void init(TWCGame game) throws SlickException {
        playerFont = Fonts.DEFAULT_2.load();
        smallFont = Fonts.DEFAULT_1.load();
    }

    @Override
    public void enter(TWCGame game) throws SlickException {

    }

    @Override
    public void update(TWCGame game, float deltaSeconds) throws SlickException {
        int cpuSum = sum(cpuActionStack);
        int playerSum = sum(playerActionStack);
        int minSum = Math.min(cpuSum, playerSum);
        int maxSum = Math.max(cpuSum, playerSum);
        if(unitsElapsed < minSum) {
            state = ControlState.WAIT;
        }
        if(state == ControlState.WAIT) {

            if(playerSum == minSum && playerNext == null) {
                float left = Math.min(1f, ((float) minSum) - unitsElapsed + 0.025f);
                unitsElapsed += deltaSeconds * unitTimeScale * left;
            } else {
                unitsElapsed += deltaSeconds * unitTimeScale;
            }


            if(unitsElapsed >= minSum) {
                unitsElapsed = minSum;
                updatePlayerState();
                if(state == ControlState.PLAYER) {
                    if (playerNext != null) {
                        playerActionStack.add(playerNext);
                        playerNext = null;
                        playerCombo++;
                        maxCombo = Math.max(maxCombo, playerCombo);
                    } else {
                        maxCombo = Math.max(maxCombo, playerCombo);
                        playerCombo = 0;
                    }
                }
                updatePlayerState();
            }
        }
        if(state == ControlState.CPU) {
            cpuActionStack.add(new Action((int)(Math.random()*7)+1, "Some Action"));
        }
    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        float pixelLength = unitsElapsed* unitDisplayScale;
        float line = game.getRenderContext().width - 9* unitDisplayScale;
        float dx = line - pixelLength;
        renderStack(graphics, game.getRenderContext().actualScale, "Player", playerActionStack, 75, dx, Color.red, playerNext);
        renderStack(graphics, game.getRenderContext().actualScale, "CPU", cpuActionStack, 150, dx, Color.cyan);
        graphics.setColor(Color.white);
        graphics.drawLine(line, 0, line, game.getRenderContext().height);

        smallFont.drawString(10, 10, String.format(
                "State: %s\nTime: %.1f\nCurrent Combo: %d\nMax Combo: %d",
                state.name(),
                unitsElapsed,
                playerCombo,
                maxCombo
        ));
        smallFont.drawString(200, 10, String.format(
                "Display Scale: %d\nTime Scale: %.2f",
                unitDisplayScale, unitTimeScale
        ));
    }

    private void renderStack(Graphics graphics, float preScale, String name, List<Action> actions, float y, float dx, Color color, Action ... additional) {
        int padding = 2;
        graphics.setColor(color);
        playerFont.drawString(20, y, name, color);
        for(Action action:actions) {
            int width = action.getDuration()* unitDisplayScale;
            graphics.setLineWidth(preScale*2);
            graphics.drawRect(dx + padding, y + playerFont.getLineHeight()*1.5f, width - (padding*2), 20);
            dx += width;
        }
        graphics.setColor(color.scaleCopy(0.5f));
        for(Action action:additional) {
            if(action != null) {
                int width = action.getDuration() * unitDisplayScale;
                graphics.setLineWidth(preScale * 2);
                graphics.drawRect(dx + padding, y + playerFont.getLineHeight() * 1.5f, width - (padding * 2), 20);
                dx += width;
            }
        }
    }

    private int sum(List<Action> actions) {
        return actions.stream().map(Action::getDuration).reduce(0, (a, b) -> a + b);
    }

    private void updatePlayerState() {
        int cpuSum = sum(cpuActionStack);
        int playerSum = sum(playerActionStack);
        state = cpuSum < playerSum ? ControlState.CPU : ControlState.PLAYER;
    }

    @Override
    public void keyPressed(TWCGame game, Key key, char c) {
        if(key != null) {
            Action action = null;
            switch (key) {
                case OPTION_1:
                    action = new Action(1, "???");
                    break;
                case OPTION_2:
                    action = new Action(2, "???");
                    break;
                case OPTION_3:
                    action = new Action(3, "???");
                    break;
                case OPTION_4:
                    action = new Action(4, "???");
                    break;
                case OPTION_5:
                    action = new Action(5, "???");
                    break;
                case OPTION_6:
                    action = new Action(6, "???");
                    break;
                case OPTION_7:
                    action = new Action(7, "???");
                    break;
                case OPTION_8:
                    action = new Action(8, "???");
                    break;
                case OPTION_9:
                    action = new Action(9, "???");
                    break;
                case LEFT:
                    unitDisplayScale = Math.max(4, unitDisplayScale-1);
                    break;
                case RIGHT:
                    unitDisplayScale = Math.min(48, unitDisplayScale+1);
                    break;
                case DOWN:
                    unitTimeScale = Math.max(1, unitTimeScale-0.25f);
                    break;
                case UP:
                    unitTimeScale = Math.min(16, unitTimeScale+0.25f);
                    break;
            }
            if(action != null) {
                if (state == ControlState.WAIT) {
                    playerNext = action;
                    updatePlayerState();
                } else if (state == ControlState.PLAYER) {
                    playerActionStack.add(action);
                    playerCombo++;
                    maxCombo = Math.max(maxCombo, playerCombo);
                    updatePlayerState();
                }
            }
        }
    }

    @Override
    public void keyReleased(TWCGame game, Key key, char c) {

    }

    @Override
    public void leave(TWCGame game) throws SlickException {

    }
}
