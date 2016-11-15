package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import com.fisherevans.twc.game.input.Key;
import com.fisherevans.twc.game.states.combat.Action.Segment;
import com.fisherevans.twc.game.states.combat.players.Player;
import com.fisherevans.twc.game.states.combat.players.skills.Skill;
import com.fisherevans.twc.game.states.combat.players.skills.SkillSegment;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CombatState implements TWCState {
    private static final Logger log = LoggerFactory.getLogger(CombatState.class);

    private int unitDisplayScale = 16;
    private float unitTimeScale = 4;

    private Player human, cpu;
    private CombatEnvironment combatEnvironment;

    private AtomicReference<Skill> playerNext = new AtomicReference(null);
    private int playerCombo = 0;
    private int maxCombo = 0;

    private AngelCodeFont playerFont, smallFont;

    public CombatState() {
        human = new Player("Fisher", 100, p -> playerNext.get());
        cpu = new Player("CPU", 100, p -> Skill.of("HAx", SkillSegment.doesNothing(((int)Math.random()*8)+1)));
        combatEnvironment = new CombatEnvironment(human, cpu);
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
        combatEnvironment.update(deltaSeconds);
    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        float pixelLength = combatEnvironment.getTimePassed()* unitDisplayScale;
        float line = game.getRenderContext().width - 9* unitDisplayScale;
        float dx = line - pixelLength;
        renderStack(graphics, game.getRenderContext().actualScale, "Player", human.getExecutedSkills(), human.get, 75, dx, Color.red, playerNext);
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
            int width = action.getTotalDuration()* unitDisplayScale;
            graphics.setLineWidth(preScale*2);
            graphics.drawRect(dx + padding, y + playerFont.getLineHeight()*1.5f, width - (padding*2), 20);
            dx += width;
        }
        graphics.setColor(color.scaleCopy(0.5f));
        for(Action action:additional) {
            if(action != null) {
                int width = action.getTotalDuration() * unitDisplayScale;
                graphics.setLineWidth(preScale * 2);
                graphics.drawRect(dx + padding, y + playerFont.getLineHeight() * 1.5f, width - (padding * 2), 20);
                dx += width;
            }
        }
    }

    private int sum(List<Action> actions) {
        return actions.stream().map(Action::getTotalDuration).reduce(0, (a, b) -> a + b);
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
                    action = new Action("Rest", new Segment(1, null));
                    break;
                case OPTION_2:
                    action = new Action("Dodge", new Segment(2, null));
                    break;
                case OPTION_3:
                    action = new Action("Jab", new Segment(3, null));
                    break;
                case OPTION_4:
                    action = new Action("Parry", new Segment(4, null));
                    break;
                case OPTION_5:
                    action = new Action("Strike", new Segment(5, null));
                    break;
                case OPTION_6:
                    action = new Action("Fireball", new Segment(6, null));
                    break;
                case OPTION_7:
                    action = new Action("Body Blow", new Segment(7, null));
                    break;
                case OPTION_8:
                    action = new Action("Recover", new Segment(8, null));
                    break;
                case OPTION_9:
                    action = new Action("Nuke'em", new Segment(9, null));
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
