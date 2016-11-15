package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.states.combat.players.Player;
import com.fisherevans.twc.game.states.combat.players.skills.Skill;
import com.fisherevans.twc.game.states.combat.players.skills.SkillSegment;

import java.util.Comparator;

class CombatEvent implements Comparable<CombatEvent> {
    private static final Comparator<CombatEvent> COMPARATOR = Comparator
            .comparing(CombatEvent::getTime)
            .thenComparing(CombatEvent::getEventName)
            .thenComparing(CombatEvent::getPlayer);

    private final Player player;
    private final Skill skill;
    private final SkillSegment segment;
    private final EventName eventName;
    private final int time;

    public CombatEvent(Player player, Skill skill, SkillSegment segment, EventName eventName, int time) {
        this.player = player;
        this.skill = skill;
        this.segment = segment;
        this.eventName = eventName;
        this.time = time;
    }

    public Player getPlayer() {
        return player;
    }

    public Skill getSkill() {
        return skill;
    }

    public SkillSegment getSegment() {
        return segment;
    }

    public EventName getEventName() {
        return eventName;
    }

    public int getTime() {
        return time;
    }

    @Override
    public int compareTo(CombatEvent other) {
        return COMPARATOR.compare(this, other);
    }

    public String toString() {
        return String.format("CombatEvent[player=%s,skill=%s,segment=%s,event=%s,time=%d]",
                player.toString(),
                String.valueOf(skill),
                String.valueOf(segment),
                String.valueOf(eventName),
                time);
    }

    // order matters, events use declare order to ensure ends happen before starts
    enum EventName { END, QUEUE_NEXT, START }
}
