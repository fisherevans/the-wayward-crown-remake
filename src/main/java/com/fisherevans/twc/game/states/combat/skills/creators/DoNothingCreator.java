package com.fisherevans.twc.game.states.combat.skills.creators;

import com.fisherevans.twc.game.rpg.skills.SkillDefinition;
import com.fisherevans.twc.game.states.combat.skills.SkillInstance;
import com.fisherevans.twc.game.states.combat.skills.SkillSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class DoNothingCreator implements SkillInstanceCreator {
    private static final Logger log = LoggerFactory.getLogger(DoNothingCreator.class);

    private final int duration;

    public DoNothingCreator(Object yamlArg) {
        duration = ((Number) ((Map) yamlArg).get("duration")).intValue();
    }

    public DoNothingCreator(int duration) {
        this.duration = duration;
    }

    @Override
    public SkillInstance create(SkillDefinition definition) {
        return new SkillInstance(
                definition,
                new SkillSegment(
                        duration,
                        (c) -> Arrays.asList(
                                () -> log.info(definition.getName() + ": " + c.toString())
                        )
                )
        );
    }
}
