package com.fisherevans.twc.game.states.combat.skills.creators;

import com.fisherevans.twc.game.states.combat.skills.SkillCombatHandler;
import com.fisherevans.twc.game.states.combat.skills.SkillSegment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;

public class DoNothingCreator implements SkillCombatHandlerCreator {
    private static final Logger log = LoggerFactory.getLogger(DoNothingCreator.class);

    private final int duration;

    public DoNothingCreator(Object config) {
        duration = ((Number) config).intValue();
    }

    public DoNothingCreator(int duration) {
        this.duration = duration;
    }

    @Override
    public SkillCombatHandler create() {
        return new SkillCombatHandler(
                new SkillSegment(
                        duration,
                        (c) -> Arrays.asList(
                                () -> log.info(c.toString())
                        )
                )
        );
    }
}
