package com.fisherevans.twc.game.states.combat.players;

import com.fisherevans.twc.game.states.combat.players.skills.Skill;

public interface SkillProvider {
    Skill nextSkill(Player owner);
}
