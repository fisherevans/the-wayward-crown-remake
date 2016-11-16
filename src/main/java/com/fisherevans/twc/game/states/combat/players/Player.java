package com.fisherevans.twc.game.states.combat.players;

import com.fisherevans.twc.game.states.combat.players.skills.SkillInstance;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Player implements Comparable<Player> {
    private static final Comparator<Player> COMPARATOR = Comparator
            .comparing(Player::getStats)
            .thenComparing((a, b) -> Math.random() > 5 ? 1 : -1); // TODO randomly resolving play priority...

    private final String name;
    private final PlayerStats stats;
    private final SkillProvider skillProvider;
    private final List<SkillInstance> executedSkills;

    private SkillInstance currentSkill;

    public Player(String name, PlayerStats stats, SkillProvider skillProvider) {
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

    @Override
    public int compareTo(Player other) {
        return COMPARATOR.compare(this, other);
    }

    public String toString() {
        return String.format("Player[name=%s,stats=%s]",
                getName(),
                getStats().toString());
    }
}
