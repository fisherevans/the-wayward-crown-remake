package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.rpg.PlayerStats;
import com.fisherevans.twc.game.states.combat.skills.SkillInstance;
import com.fisherevans.twc.game.states.combat.skills.SkillSegment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CombatPlayer implements Comparable<CombatPlayer> {
    private static final Comparator<CombatPlayer> COMPARATOR = Comparator
            .comparing(CombatPlayer::getStats)
            .thenComparing((a, b) -> Math.random() > 5 ? 1 : -1); // TODO randomly resolving play priority...

    private final String name;
    private final PlayerStats stats;
    private final SkillProvider skillProvider;
    private final List<SkillInstance> executedSkills;

    private SkillInstance currentSkill;
    private SkillSegment currentSegment;

    public CombatPlayer(String name, PlayerStats stats, SkillProvider skillProvider) {
        this.name = name;
        this.stats = stats;
        this.skillProvider = skillProvider;
        this.executedSkills = new ArrayList();

        this.currentSkill = null;
    }

    public String getName() {
        return name;
    }

    public PlayerStats getStats() {
        return stats;
    }

    public List<SkillInstance> getExecutedSkills() {
        return executedSkills;
    }

    public SkillProvider getSkillProvider() {
        return skillProvider;
    }

    public void setCurrentSkill(SkillInstance currentSkill) {
        this.currentSkill = currentSkill;
    }

    public SkillInstance getCurrentSkill() {
        return currentSkill;
    }

    public SkillSegment getCurrentSegment() {
        return currentSegment;
    }

    public void setCurrentSegment(SkillSegment currentSegment) {
        this.currentSegment = currentSegment;
    }

    @Override
    public int compareTo(CombatPlayer other) {
        return COMPARATOR.compare(this, other);
    }

    public String toString() {
        return String.format("CombatPlayer[name=%s,stats=%s]",
                getName(),
                getStats().toString());
    }

    public static interface SkillProvider {
        SkillInstance nextSkill(CombatPlayer owner);
    }
}
