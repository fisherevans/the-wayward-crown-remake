package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import com.fisherevans.twc.game.input.Key;
import com.fisherevans.twc.game.rpg.skills.SkillDefinition;
import com.fisherevans.twc.game.rpg.skills.SkillGroup;
import com.fisherevans.twc.game.rpg.skills.SkillGroup.GraphNode;
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
import java.util.stream.Collectors;

public class CombatState implements TWCState {
    private static final Logger log = LoggerFactory.getLogger(CombatState.class);

    private int unitDisplayScale = 16;
    private float unitTimeScale = 4;
    private float slowTimeAfter = 0.75f;

    private CombatPlayer human, cpu;
    private CombatEnvironment combatEnvironment;

    private AtomicReference<GraphNode> playerNext = new AtomicReference(null);
    private int playerCombo = 0;
    private int maxCombo = 0;

    private AngelCodeFont playerFont, smallFont;

    private class ActiveSkill {
        final GraphNode baseSkill;
        List<GraphNode> active;
        public ActiveSkill(GraphNode baseSkill) {
            this.baseSkill = baseSkill;
            reset();
        }
        private void step(GraphNode from) {
            if(from.getFollowedBy() == null || from.getFollowedBy().size() == 0) {
                reset();
            } else {
                active = new LinkedList();
                active.addAll(from.getFollowedBy());
            }
        }
        private void reset() {
            this.active = new LinkedList();
            this.active.add(this.baseSkill);
        }
    }

    private List<ActiveSkill> skills;
    private Integer expandSkill = null;

    public CombatState() {
        human = new CombatPlayer(
                "Human",
                new PlayerStats(100, 5),
                p -> {
                    GraphNode next = playerNext.getAndSet(null);
                    playerCombo = next == null ? 0 : playerCombo + 1;
                    maxCombo = Math.max(maxCombo, playerCombo);
                    if(next != null) {
                        for (ActiveSkill activeSkill : skills) {
                            if (activeSkill.active.contains(next)) {
                                activeSkill.step(next);
                            } else {
                                activeSkill.reset();
                            }
                        }
                        expandSkill = null;
                    }
                    return next == null ? null : next.getSkill().createInstance();
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

        skills = game.getSkillGroupRegistry().getIds()
                .stream()
                .map(game.getSkillGroupRegistry()::get)
                .map(SkillGroup::getRootNode)
                .map(ActiveSkill::new)
                .collect(Collectors.toList());
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
        float line = game.getRenderContext().width/2f;
        float dx = line - pixelLength;
        renderStack(graphics, game.getRenderContext().actualScale, human, 60, dx, Color.red,
                playerNext.get() == null ? null : playerNext.get().getSkill().createInstance()); // TODO sloppy
        renderStack(graphics, game.getRenderContext().actualScale, cpu, 110, dx, Color.cyan, null);
        graphics.setColor(Color.white);
        graphics.drawLine(line, 0, line, game.getRenderContext().height);

        smallFont.drawString(10, 10, String.format(
                "Time: %.1f\nCurrent Combo: %d\nMax Combo: %d\nExpanded: %s",
                combatEnvironment.getTimePassed(),
                playerCombo,
                maxCombo,
                String.valueOf(expandSkill)
        ));
        smallFont.drawString(game.getRenderContext().width/2f + 10, 10, String.format(
                "Display Scale: %d\nTime Scale: %.2f\nLeft: %.2f",
                unitDisplayScale, unitTimeScale, combatEnvironment.getNextEvent(human, EventName.QUEUE_NEXT).getTime() - combatEnvironment.getTimePassed()
        ));

        int y = 170;
        graphics.setLineWidth(game.getRenderContext().actualScale);
        int id = 0;
        for(ActiveSkill activeSkill:skills) {
            graphics.setColor(expandSkill == null || id == expandSkill ? Color.white : Color.darkGray);
            graphics.drawRect(19, y-1, 18, 18);
            if(activeSkill.active.size() == 1) {
                SkillDefinition def = activeSkill.active.get(0).getSkill();
                graphics.drawImage(def.getSmallIcon(), 20, y);
                smallFont.drawString(40, y+3, def.getName());
            } else {
                smallFont.drawString(40, y+3, "Multiple...");
            }
            y += 20;
            id++;
        }

        y = 170;
        int x = (int) (game.getRenderContext().width/2) + 20;
        if(expandSkill != null) {
            ActiveSkill activeSkill = skills.get(expandSkill);
            id = 0;
            for(GraphNode node:activeSkill.active) {
                graphics.setColor(Color.white);
                graphics.drawRect(x, y-1, 18, 18);
                graphics.drawImage(node.getSkill().getSmallIcon(), x, y);
                smallFont.drawString(x+20, y+3, node.getSkill().getName());
                y += 20;
                id++;
            }
        }
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
            graphics.drawRect(dx + padding, y + playerFont.getLineHeight()*1.15f, width - (padding*2), unitDisplayScale - padding*2);
            dx += width;
        }
    }

    @Override
    public void keyPressed(TWCGame game, Key key, char c) {
        if(key != null) {
            int duration = 0;
            switch (key) {
                case OPTION_1: select(0); break;
                case OPTION_2: select(1); break;
                case OPTION_3: select(2); break;
                case OPTION_4: select(3); break;
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
                case BACK:
                    expandSkill = null;
                    break;
            }
            if(duration > 0) {
                playerNext.set(new GraphNode(SkillDefinition.doNothing(duration)));
            }
        }
    }

    private void select(int id) {
        if(expandSkill == null) {
            ActiveSkill activeSkill = skills.get(id);
            if (activeSkill != null) {
                if (activeSkill.active.size() == 1) {
                    playerNext.set(activeSkill.active.get(0));
                } else if (activeSkill.active.size() > 1) {
                    expandSkill = id;
                }
            }
        } else {
            ActiveSkill activeSkill = skills.get(expandSkill);
            if(activeSkill.active.size() > id) {
                playerNext.set(activeSkill.active.get(id));
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
