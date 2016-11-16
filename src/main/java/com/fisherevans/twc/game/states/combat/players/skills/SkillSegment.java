package com.fisherevans.twc.game.states.combat.players.skills;

import com.fisherevans.twc.game.states.combat.CombatEnvironment;
import com.fisherevans.twc.game.states.combat.CombatEnvironment.Action;
import com.fisherevans.twc.game.states.combat.players.Player;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public interface SkillSegment {
    int duration();
    List<Action> handleEvent(EventContext context);

    static SkillSegment of(final int duration, Function<EventContext, List<Action>> handler) {
        return new SkillSegment() {
            public int duration() { return duration; }
            public List<Action> handleEvent(EventContext context) { return handler.apply(context); }
        };
    }

    static SkillSegment of(final int duration,
                           Function<EventContext, List<Action>> start,
                           Function<EventContext, List<Action>> interrupt,
                           Function<EventContext, List<Action>> end) {
        return of(duration, context -> {
                Function<EventContext, List<Action>> f = null;
                switch (context.eventType()) {
                    case START: f = start; break;
                    case INTERRUPT: f = interrupt; break;
                    case END: f = end; break;
                }
                return f == null ? Arrays.asList() : f.apply(context);
        });
    }

    enum EventType { START, INTERRUPT, END }

    interface EventContext {
        CombatEnvironment combatEnvironment();
        EventType eventType();
        EventPlayerContext owner();
        EventPlayerContext opponent();

        static EventContext of(CombatEnvironment environment, EventType eventType, EventPlayerContext owner, EventPlayerContext opponent) {
            return new EventContext() {
                public CombatEnvironment combatEnvironment() { return environment; }
                public EventType eventType() { return eventType; }
                public EventPlayerContext owner() { return owner; }
                public EventPlayerContext opponent() { return opponent; }
            };
        }
    }

    interface EventPlayerContext {
        Player player();
        SkillInstance skill();
        SkillSegment segment();

        static EventPlayerContext of(Player player, SkillInstance skill, SkillSegment segment) {
            return  new EventPlayerContext() {
                public Player player() { return player; }
                public SkillInstance skill() { return skill; }
                public SkillSegment segment() { return segment; }
            };
        }
    }
}
