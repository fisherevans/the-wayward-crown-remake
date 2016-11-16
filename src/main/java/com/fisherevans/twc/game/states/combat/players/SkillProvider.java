package com.fisherevans.twc.game.states.combat.players;

import com.fisherevans.twc.game.states.combat.players.skills.SkillInstance;

public interface SkillProvider {
    SkillInstance nextSkill(Player owner);
}
