package com.fisherevans.twc.game.rpg;

import com.fisherevans.twc.game.states.combat.players.skills.SkillInstance;
import org.newdawn.slick.Image;

public interface SkillDefinition {
    String name();
    String description();
    Image smallIcon();
    Image largeIcon();

    SkillInstance createInstance();
}
