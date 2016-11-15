package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.states.combat.CombatEvent.EventName;
import com.fisherevans.twc.game.states.combat.players.Player;
import com.fisherevans.twc.game.states.combat.players.skills.Action;
import com.fisherevans.twc.game.states.combat.players.skills.Skill;
import com.fisherevans.twc.game.states.combat.players.skills.SkillSegment;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class CombatEnvironment {
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
                    if(skill != null) {
                        player.getExecutedSkills().add(skill);
                    }
                    Skill nextSkill = player.getSkillProvider().nextSkill(player);
                    if(nextSkill == null) {
                        // if one can't be found - set the new time to "now" to end the loop
                        // DON'T DELETE FROM STACK
                        executeUntil = event.getTime();
                    } else {
                        queueSegments(player, nextSkill, event.getTime());
                        eventStack.pollFirst();
                    }
                    break;
                }
                case START: {
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
        eventStack.add(new CombatEvent(player, skill, null, EventName.QUEUE_NEXT, time));
    }

    private Set<Player> getOpponents(Player owner) {
        return players.stream().filter(p -> !p.equals(owner)).collect(Collectors.toSet());
    }

    public float getTimePassed() {
        return timePassed;
    }
}
