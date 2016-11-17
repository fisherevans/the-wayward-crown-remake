package com.fisherevans.twc.game.states.combat.skills;

import com.fisherevans.twc.game.states.combat.CombatEnvironment.Action;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class SkillSegment {
    private final int duration;
    private final Function<SegmentNotification, List<Action>> listener;

    public SkillSegment(int duration,
                        final Function<SegmentNotification, List<Action>> start,
                        final Function<SegmentNotification, List<Action>> interrupt,
                        final Function<SegmentNotification, List<Action>> end) {
        this(duration, context -> {
            Function<SegmentNotification, List<Action>> f = null;
            switch (context.getEventType()) {
                case START: f = start; break;
                case INTERRUPT: f = interrupt; break;
                case END: f = end; break;
            }
            return f == null ? Arrays.asList() : f.apply(context);
        });
    }

    public SkillSegment(int duration, Function<SegmentNotification, List<Action>> listener) {
        this.duration = duration;
        this.listener = listener;
    }

    public int getDuration() {
        return duration;
    }

    public List<Action> listen(SegmentNotification context) {
        return listener == null ? Arrays.asList() : listener.apply(context);
    }
}

