package com.fisherevans.twc.game.rpg;

import com.fisherevans.twc.game.states.combat.skills.SkillInstance;
import com.fisherevans.twc.game.states.combat.skills.SkillSegment;
import org.newdawn.slick.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

public class SkillDefinition {
    private static final Logger log = LoggerFactory.getLogger(SkillDefinition.class);

    private final String name;
    private final String description;
    private final Image smallIcon;
    private final Image largeIcon;
    private final SkillInstanceCreator skillInstanceCreator;

    public SkillDefinition(String name, String description, Image smallIcon, Image largeIcon, SkillInstanceCreator skillInstanceCreator) {
        this.name = name;
        this.description = description;
        this.smallIcon = smallIcon;
        this.largeIcon = largeIcon;
        this.skillInstanceCreator = skillInstanceCreator;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getSmallIcon() {
        return smallIcon;
    }

    public Image getLargeIcon() {
        return largeIcon;
    }

    public SkillInstance createInstance() {
        return skillInstanceCreator.create(this);
    }

    public interface SkillInstanceCreator {
        SkillInstance create(SkillDefinition definition);
    }

    public static SkillDefinition doNothing(int duration) {
        return new SkillDefinition(
                "Do Nothing",
                "Just sit there. Doing nothing. Like an idiot. For like " + duration + " units.",
                null, null,
                (d) -> new SkillInstance(
                        d,
                        new SkillSegment(
                                duration,
                                (c) -> Arrays.asList(
                                        () -> log.info("DoNothing: " + c.toString())
                                )
                        )
                )
        );
    }
}
