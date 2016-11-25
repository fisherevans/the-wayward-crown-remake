package com.fisherevans.twc.game.states.combat.skills;

import com.fisherevans.twc.util.imut_col.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SkillCombatHandler {
    private final List<SkillSegment> segments;
    private final int totalDuration;

    public SkillCombatHandler(SkillSegment ... segments) {
        this(Arrays.asList(segments));
    }

    public SkillCombatHandler(List<SkillSegment> segments) {
        this.segments = new ArrayList();
        this.segments.addAll(segments);
        this.totalDuration = this.segments.stream().map(SkillSegment::getDuration).reduce(0, (a, b) -> a + b);
    }

    public List<SkillSegment> getSegments() {
        return new ImmutableList(segments);
    }

    public int getTotalDuration() {
        return totalDuration;
    }
}
