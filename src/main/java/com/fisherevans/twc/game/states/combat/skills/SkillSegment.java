package com.fisherevans.twc.game.states.combat.skills;

import com.fisherevans.twc.game.states.combat.CombatEnvironment.Action;

import java.util.Arrays;
import java.util.List;

public class SkillSegment {
    private final int duration;
    private final SegmentHandler handler;

    public SkillSegment(int duration,
                        final SegmentHandler start,
                        final SegmentHandler interrupt,
                        final SegmentHandler end) {
        this(duration, notification -> {
            SegmentHandler handler = null;
            switch (notification.getEventType()) {
                case START: handler = start; break;
                case INTERRUPT: handler = interrupt; break;
                case END: handler = end; break;
            }
            return handler == null ? Arrays.asList() : handler.handle(notification);
        });
    }

    public SkillSegment(int duration, SegmentHandler handler) {
        this.duration = duration;
        this.handler = handler;
    }

    public int getDuration() {
        return duration;
    }

    public SegmentHandler getHandler() {
        return handler;
    }

    public interface SegmentHandler {
        List<Action> handle(SegmentNotification notification);
    }
}

