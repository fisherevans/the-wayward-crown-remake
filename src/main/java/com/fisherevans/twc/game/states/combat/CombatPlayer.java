package com.fisherevans.twc.game.states.combat;

import com.fisherevans.twc.game.rpg.stats.PlayerStats;
import com.fisherevans.twc.game.rpg.skills.SkillDefinition;
import com.fisherevans.twc.game.rpg.stats.StatsModifier;
import com.fisherevans.twc.game.states.combat.skills.SkillSegment;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CombatPlayer implements Comparable<CombatPlayer> {
    private static final Comparator<CombatPlayer> COMPARATOR = Comparator
            .comparing(CombatPlayer::getEffectiveStats)
            .thenComparing(CombatPlayer::getBaseStats)
            .thenComparing((a, b) -> Math.random() > 5 ? 1 : -1); // TODO randomly resolving play priority...

    private final String name;
    private final SkillProvider skillProvider;
    private final List<SkillDefinition> executedSkills;

    private SkillDefinition currentSkill;
    private SkillSegment currentSegment;

    private final StatsModifier statsModifier;
    private final PlayerStats baseStats, effectiveStats;


    public CombatPlayer(String name, PlayerStats baseStats, SkillProvider skillProvider) {
        this.name = name;
        this.baseStats = baseStats;
        this.skillProvider = skillProvider;
        this.executedSkills = new ArrayList();

        this.currentSkill = null;

        this.statsModifier = new StatsModifier();
        this.effectiveStats = baseStats.joinedWith(statsModifier.asPlayerStats());
    }

    public String getName() {
        return name;
    }

    public List<SkillDefinition> getExecutedSkills() {
        return executedSkills;
    }

    public SkillProvider getSkillProvider() {
        return skillProvider;
    }

    public void setCurrentSkill(SkillDefinition currentSkill) {
        this.currentSkill = currentSkill;
    }

    public SkillDefinition getCurrentSkill() {
        return currentSkill;
    }

    public SkillSegment getCurrentSegment() {
        return currentSegment;
    }

    public void setCurrentSegment(SkillSegment currentSegment) {
        this.currentSegment = currentSegment;
    }

    public PlayerStats getBaseStats() {
        return baseStats;
    }

    public StatsModifier getStatsModifier() {
        return statsModifier;
    }

    public PlayerStats getEffectiveStats() {
        return effectiveStats;
    }

    @Override
    public int compareTo(CombatPlayer other) {
        return COMPARATOR.compare(this, other);
    }

    public String toString() {
        return String.format("CombatPlayer[name=%s,baseStats=%s]",
                getName(),
                getBaseStats().toString());
    }

    public interface SkillProvider {
        SkillDefinition nextSkill(CombatPlayer owner);
    }
}
