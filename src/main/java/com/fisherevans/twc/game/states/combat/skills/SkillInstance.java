package com.fisherevans.twc.game.states.combat.skills;

import com.fisherevans.twc.game.rpg.skills.SkillDefinition;

import java.util.Arrays;
import java.util.List;

public class SkillInstance {
    private final SkillDefinition definition;
    private final List<SkillSegment> segments;

    public SkillInstance(SkillDefinition definition, SkillSegment ... segments) {
        this(definition, Arrays.asList(segments));
    }

    public SkillInstance(SkillDefinition definition, List<SkillSegment> segments) {
        this.definition = definition;
        this.segments = segments;
    }

    public SkillDefinition getDefinition() {
        return definition;
    }

    public List<SkillSegment> getSegments() {
        return segments;
    }

    public int getTotalSegmentDuration() {
        return segments.stream().map(SkillSegment::getDuration).reduce(0, (a, b) -> a + b);
    }
}
