package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import com.fisherevans.twc.game.input.Key;
import com.fisherevans.twc.game.rpg.SkillDefinition;
import com.fisherevans.twc.game.states.combat.CombatEvent.EventName;
import com.fisherevans.twc.game.rpg.PlayerStats;
import com.fisherevans.twc.game.states.combat.skills.SkillInstance;
import com.fisherevans.twc.util.MathUtil;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class CombatState implements TWCState {
    private static final Logger log = LoggerFactory.getLogger(CombatState.class);

    private int unitDisplayScale = 16;
    private float unitTimeScale = 4;
    private float slowTimeAfter = 0.75f;

    private CombatPlayer human, cpu;
    private CombatEnvironment combatEnvironment;

    private AtomicReference<SkillInstance> playerNext = new AtomicReference(null);
    private int playerCombo = 0;
    private int maxCombo = 0;

    private AngelCodeFont playerFont, smallFont;

    public CombatState() {
        human = new CombatPlayer(
                "Human",
                new PlayerStats(100, 5),
                p -> {
                    SkillInstance next = playerNext.getAndSet(null);
                    playerCombo = next == null ? 0 : playerCombo + 1;
                    maxCombo = Math.max(maxCombo, playerCombo);
                    return next;
                });
        cpu = new CombatPlayer(
                "CPU",
                new PlayerStats(100, 3),
                p -> SkillDefinition.doNothing(MathUtil.randomInt(1, 8)).createInstance());
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
        float combatDelta = deltaSeconds*unitTimeScale;
        float humanTimeLeft = combatEnvironment.getNextEvent(human, EventName.QUEUE_NEXT).getTime() - combatEnvironment.getTimePassed();
        if(humanTimeLeft < slowTimeAfter && playerNext.get() == null) {
            combatDelta = Math.max(deltaSeconds*0.1f, combatDelta*(humanTimeLeft/slowTimeAfter));
        }
        combatEnvironment.update(combatDelta);
    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        float pixelLength = combatEnvironment.getTimePassed()* unitDisplayScale;
        float line = game.getRenderContext().width - (9*2*unitDisplayScale);
        line = game.getRenderContext().width/2f;
        float dx = line - pixelLength;
        renderStack(graphics, game.getRenderContext().actualScale, human, 75, dx, Color.red, playerNext.get());
        renderStack(graphics, game.getRenderContext().actualScale, cpu, 150, dx, Color.cyan, null);
        graphics.setColor(Color.white);
        graphics.drawLine(line, 0, line, game.getRenderContext().height);

        smallFont.drawString(10, 10, String.format(
                "Time: %.1f\nCurrent Combo: %d\nMax Combo: %d",
                combatEnvironment.getTimePassed(),
                playerCombo,
                maxCombo
        ));
        smallFont.drawString(game.getRenderContext().width/2f + 10, 10, String.format(
                "Display Scale: %d\nTime Scale: %.2f\nLeft: %.2f",
                unitDisplayScale, unitTimeScale, combatEnvironment.getNextEvent(human, EventName.QUEUE_NEXT).getTime() - combatEnvironment.getTimePassed()
        ));
    }

    private void renderStack(Graphics graphics, float preScale, CombatPlayer player, float y, float dx, Color color, SkillInstance queuedSkill) {
        int padding = 2;
        graphics.setColor(color);
        playerFont.drawString(20, y, player.getName(), color);

        class SkillRenderObject {
            public final SkillInstance skill;
            public final Color color;
            public SkillRenderObject(SkillInstance skill, Color color) {
                this.skill = skill;
                this.color = color;
            }
        }

        List<SkillRenderObject> renderList = new LinkedList();
        player.getExecutedSkills().stream().map(s -> new SkillRenderObject(s, color)).forEach(renderList::add);
        if(player.getCurrentSkill() != null) {
            renderList.add(new SkillRenderObject(player.getCurrentSkill(), color.scaleCopy(0.8f)));
        }
        if(queuedSkill != null) {
            renderList.add(new SkillRenderObject(queuedSkill, color.scaleCopy(0.5f)));
        }

        graphics.setLineWidth(preScale*2);
        for(SkillRenderObject sro:renderList) {
            int width = sro.skill.getTotalSegmentDuration() * unitDisplayScale;
            graphics.setColor(sro.color);
            graphics.drawRect(dx + padding, y + playerFont.getLineHeight()*1.5f, width - (padding*2), unitDisplayScale - padding*2);
            dx += width;
        }
    }

    @Override
    public void keyPressed(TWCGame game, Key key, char c) {
        if(key != null) {
            int duration = 0;
            switch (key) {
                case OPTION_1: duration = 1; break;
                case OPTION_2: duration = 2; break;
                case OPTION_3: duration = 3; break;
                case OPTION_4: duration = 4; break;
                case OPTION_5: duration = 5; break;
                case OPTION_6: duration = 6; break;
                case OPTION_7: duration = 7; break;
                case OPTION_8: duration = 8; break;
                case OPTION_9: duration = 9; break;
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
            if(duration > 0) {
                playerNext.set(SkillDefinition.doNothing(duration).createInstance());
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
