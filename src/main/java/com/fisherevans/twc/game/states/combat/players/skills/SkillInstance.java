package com.fisherevans.twc.game.states.combat.players.skills;

import com.fisherevans.twc.game.rpg.SkillDefinition;

import java.util.Arrays;
import java.util.List;

public interface SkillInstance {
    SkillDefinition definition();
    List<SkillSegment> segments();

    default int totalSegmentDuration() {
        return segments() == null ? 0
                : segments().stream()
                    .map(SkillSegment::duration)
                    .reduce(0, (a, b) -> a + b);
    }

    static SkillInstance of(final SkillDefinition definition, SkillSegment ... segments) {
        return of(definition, Arrays.asList(segments));
    }

    static SkillInstance of(final SkillDefinition definition, final List<SkillSegment> segments) {
        return new SkillInstance() {
            @Override public SkillDefinition definition() { return definition; }
            @Override public List<SkillSegment> segments() { return segments; }
        };
    }
}
