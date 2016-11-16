package com.fisherevans.twc.game.rpg.skills;

import com.fisherevans.twc.game.rpg.SkillDefinition;
import com.fisherevans.twc.game.states.combat.players.skills.SkillInstance;
import com.fisherevans.twc.game.states.combat.players.skills.SkillSegment;
import org.newdawn.slick.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class DoNothing implements SkillDefinition {
    private static final Logger log = LoggerFactory.getLogger(DoNothing.class);

    private final int duration;

    public DoNothing(int duration) {
        this.duration = duration;
    }

    public String name() {
        return "Do Nothing";
    }

    public String description() {
        return "Just sit there. Doing nothing. Like an idiot. For like " + duration + " units.";
    }

    public Image smallIcon() {
        return null;
    }

    public Image largeIcon() {
        return null;
    }

    @Override
    public SkillInstance createInstance() {
        return SkillInstance.of(
                this,
                SkillSegment.of(
                        duration,
                        (e) -> Arrays.asList(
                                () -> log.info("DoNothing: " + e.toString())
                        )
                )
        );
    }
}
