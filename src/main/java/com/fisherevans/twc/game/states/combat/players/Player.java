package com.fisherevans.twc.game.states.combat.players;

import com.fisherevans.twc.game.states.combat.players.skills.Skill;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int health;
    private SkillProvider skillProvider;

    private List<Skill> executedSkills;

    public Player(String name, int health, SkillProvider skillProvider) {
        this.name = name;
        this.health = health;
        this.skillProvider = skillProvider;
        this.executedSkills = new ArrayList();
    }

    public String getName() {
        return name;
    }

    public int getHealth() {
        return health;
    }

    public List<Skill> getExecutedSkills() {
        return executedSkills;
    }

    public SkillProvider getSkillProvider() {
        return skillProvider;
    }
}
