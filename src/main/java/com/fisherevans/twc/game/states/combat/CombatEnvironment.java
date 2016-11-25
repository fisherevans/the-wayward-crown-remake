package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.rpg.skills.SkillDefinition;
import com.fisherevans.twc.game.states.combat.CombatEvent.EventName;
import com.fisherevans.twc.game.states.combat.skills.SegmentNotification.NotificationType;
import com.fisherevans.twc.game.states.combat.skills.SkillCombatHandler;
import com.fisherevans.twc.game.states.combat.skills.SkillSegment;
import com.fisherevans.twc.game.states.combat.skills.SegmentNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.TreeSet;

public class CombatEnvironment {
    private static final Logger log = LoggerFactory.getLogger(CombatEnvironment.class);

    private final CombatPlayer player1, player2;
    private TreeSet<CombatEvent> eventStack;
    private float timePassed;

    public CombatEnvironment(CombatPlayer player1, CombatPlayer player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.eventStack = new TreeSet();
        this.timePassed = 0f;
        eventStack.add(new CombatEvent(player1, null, null, EventName.QUEUE_NEXT, 0));
        eventStack.add(new CombatEvent(player2, null, null, EventName.QUEUE_NEXT, 0));
        log.info("Initial event stack: " + String.valueOf(eventStack));
    }

    public void update(float timeDelta) {
        float executeUntil = timePassed + timeDelta;
        CombatEvent event;
        while((event = eventStack.first()).getTime() < executeUntil) {
            CombatPlayer player = event.getPlayer();
            timePassed = event.getTime();
            switch (event.getEventName()) {
                case END:
                case START: {
                    player.setCurrentSegment(event.getSegment());
                    event.getSegment().getHandler().handle(new SegmentNotification(
                            this,
                            event.getEventName() == EventName.START ? NotificationType.START : NotificationType.END,
                            player, getOpponent(player))
                    ).forEach(Action::execute);
                    eventStack.pollFirst();
                    break;
                }
                case QUEUE_NEXT: {
                    SkillDefinition nextSkill = player.getSkillProvider().nextSkill(player);
                    if(nextSkill == null) {
                        // if one can't be found - set the new time to "now" to end the loop
                        // DON'T DELETE FROM STACK
                        executeUntil = event.getTime();
                    } else {
                        if(player.getCurrentSkill() != null) {
                            player.getExecutedSkills().add(player.getCurrentSkill());
                        }
                        player.setCurrentSkill(nextSkill);
                        player.setCurrentSegment(null);
                        queueSegments(player, nextSkill.getCombatHandler(), event.getTime());
                        eventStack.pollFirst();
                    }
                    break;
                }
            }
        }
        timePassed = executeUntil;
    }

    private void queueSegments(CombatPlayer player, SkillCombatHandler skill, int time) {
        for(SkillSegment segment:skill.getSegments()) {
            eventStack.add(new CombatEvent(player, skill, segment, EventName.START, time));
            time += segment.getDuration();
            eventStack.add(new CombatEvent(player, skill, segment, EventName.END, time));
        }
        eventStack.add(new CombatEvent(player, null, null, EventName.QUEUE_NEXT, time));
    }

    private CombatPlayer getOpponent(CombatPlayer player) {
        if(player1.equals(player)) {
            return player2;
        } else if(player2.equals(player)) {
            return player1;
        } else {
            throw new RuntimeException("Asking about a player I don't know about! " + String.valueOf(player));
        }
    }

    public CombatEvent getNextEvent(CombatPlayer player, CombatEvent.EventName eventName) {
        return getNextEvent(player, eventName, false);
    }

    public CombatEvent getNextEvent(CombatPlayer player, CombatEvent.EventName eventName, boolean reverseSearch) {
        Iterator<CombatEvent> iterator = reverseSearch ? eventStack.descendingIterator() : eventStack.iterator();
        while(iterator.hasNext()) {
            CombatEvent combatEvent = iterator.next();
            if((player == null || player.equals(combatEvent.getPlayer()))
                && (eventName == null || eventName.equals(combatEvent.getEventName()))) {
                return combatEvent;
            }
        }
        return null;
    }

    public float getTimePassed() {
        return timePassed;
    }

    public interface Action {
        void execute();
    }
}
