package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.states.combat.CombatEvent.EventName;
import com.fisherevans.twc.game.states.combat.players.Player;
import com.fisherevans.twc.game.states.combat.players.skills.Action;
import com.fisherevans.twc.game.states.combat.players.skills.Skill;
import com.fisherevans.twc.game.states.combat.players.skills.SkillSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CombatEnvironment {
    Logger log = LoggerFactory.getLogger(CombatEnvironment.class);

    private final Set<Player> players;
    private TreeSet<CombatEvent> eventStack;
    private float timePassed;

    public CombatEnvironment(Set<Player> players) {
        this.players = players;
        this.eventStack = new TreeSet();
        this.timePassed = 0f;
        this.players.stream()
                .map(player -> new CombatEvent(player, null, null, EventName.QUEUE_NEXT, 0))
                .forEach(eventStack::add);
        log.info("Initial event stack: " + String.valueOf(eventStack));
    }

    public CombatEnvironment(Player ... players) {
        this(Arrays.stream(players).collect(Collectors.toSet()));
    }

    public void update(float timeDelta) {
        float executeUntil = timePassed + timeDelta;
        CombatEvent event;
        while((event = eventStack.first()).getTime() < executeUntil) {
            Player player = event.getPlayer();
            Skill skill = event.getSkill();
            SkillSegment segment = event.getSegment();
            timePassed = event.getTime();
            switch (event.getEventName()) {
                case END: {
                    log.info("Processing END event: " + String.valueOf(event));
                    segment.endListener().end(
                            player,
                            skill,
                            segment,
                            getOpponents(player)
                    ).forEach(Action::execute);
                    eventStack.pollFirst();
                    break;
                }
                case QUEUE_NEXT: {
                    Skill nextSkill = player.getSkillProvider().nextSkill(player);
                    if(nextSkill == null) {
                        // if one can't be found - set the new time to "now" to end the loop
                        // DON'T DELETE FROM STACK
                        executeUntil = event.getTime();
                    } else {
                        log.info("Queueing next skill for " + player.toString() + ": " + String.valueOf(nextSkill));
                        if(player.getCurrentSkill() != null) {
                            player.getExecutedSkills().add(player.getCurrentSkill());
                        }
                        player.setCurrentSkill(nextSkill);
                        queueSegments(player, nextSkill, event.getTime());
                        eventStack.pollFirst();
                    }
                    break;
                }
                case START: {
                    log.info("Processing START event: " + String.valueOf(event));
                    segment.startListener().start(
                            player,
                            skill,
                            segment,
                            getOpponents(player)
                    ).forEach(Action::execute);
                    eventStack.pollFirst();
                    break;
                }
            }
        }
        timePassed = executeUntil;
    }

    private void queueSegments(Player player, Skill skill, int time) {
        for(SkillSegment segment:skill.segments()) {
            eventStack.add(new CombatEvent(player, skill, segment, EventName.START, time));
            time += segment.duration();
            eventStack.add(new CombatEvent(player, skill, segment, EventName.END, time));
        }
        eventStack.add(new CombatEvent(player, null, null, EventName.QUEUE_NEXT, time));
    }

    private Set<Player> getOpponents(Player owner) {
        return players.stream().filter(p -> !p.equals(owner)).collect(Collectors.toSet());
    }

    public float getTimePassed() {
        return timePassed;
    }
}
