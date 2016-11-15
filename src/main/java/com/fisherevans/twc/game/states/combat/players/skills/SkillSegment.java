package com.fisherevans.twc.game.states.combat.players.skills;

import com.fisherevans.twc.game.states.combat.players.SkillProvider;
import com.fisherevans.twc.game.states.combat.players.skills.SegmentListener.End;
import com.fisherevans.twc.game.states.combat.players.skills.SegmentListener.Interrupt;
import com.fisherevans.twc.game.states.combat.players.skills.SegmentListener.Start;

public interface SkillSegment {
    int duration();
    SegmentListener.Start startListener();
    SegmentListener.Interrupt interruptListener();
    SegmentListener.End endListener();

    static SkillSegment doesNothing(final int duration) {
        return new SkillSegment() {
            public int duration() { return duration; }
            public Start startListener() { return Start.empty; }
            public Interrupt interruptListener() { return Interrupt.empty; }
            public End endListener() { return End.empty; }
        }
    }
}
