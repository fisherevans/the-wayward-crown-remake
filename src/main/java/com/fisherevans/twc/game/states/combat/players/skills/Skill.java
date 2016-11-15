package com.fisherevans.twc.game.states.combat.players.skills;

import java.util.Arrays;
import java.util.List;

public interface Skill {
    String name();
    List<SkillSegment> segments();

    default int totalSegmentDuration() {
        return segments() == null ? 0
                : segments().stream()
                    .map(SkillSegment::duration)
                    .reduce(0, (a, b) -> a + b);
    }

    static Skill of(final String name, final SkillSegment ... skillSegments) {
        final List<SkillSegment> segments = Arrays.asList(skillSegments);
        return new Skill() {
            public String name() { return name; }
            public List<SkillSegment> segments() { return segments; }
            public String toString() {
                return String.format("Skill[name=%s,segments=%d]",
                        name(),
                        segments().size());
            }
        };
    }
}
