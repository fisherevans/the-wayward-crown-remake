package com.fisherevans.twc.game.states.combat.skills.creators;

import com.fisherevans.twc.game.rpg.skills.SkillDefinition;
import com.fisherevans.twc.game.states.combat.skills.SkillInstance;

public interface SkillInstanceCreator {
    SkillInstance create(SkillDefinition definition);
}
