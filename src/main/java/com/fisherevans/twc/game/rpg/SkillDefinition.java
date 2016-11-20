package com.fisherevans.twc.game.rpg;

import com.fisherevans.twc.game.states.combat.skills.SkillInstance;
import com.fisherevans.twc.game.states.combat.skills.creators.DoNothingCreator;
import com.fisherevans.twc.game.states.combat.skills.creators.SkillInstanceCreator;
import org.newdawn.slick.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class SkillDefinition {
    private static final Logger log = LoggerFactory.getLogger(SkillDefinition.class);

    private final String id;
    private final String name;
    private final String description;
    private final Image smallIcon;
    private final Image largeIcon;
    private final SkillInstanceCreator skillInstanceCreator;

    public SkillDefinition(String id, String name, String description, Image smallIcon, Image largeIcon, SkillInstanceCreator skillInstanceCreator) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.smallIcon = smallIcon;
        this.largeIcon = largeIcon;
        this.skillInstanceCreator = skillInstanceCreator;
    }

    public String getId() {
        return id;
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

    @Override
    public String toString() {
        return String.format("SkillDefinition[id=%s,name='%s',desc='%s',sIcon=%s,lIcon=%s,instC=%s]",
                id,
                name,
                description,
                String.valueOf(smallIcon),
                String.valueOf(largeIcon),
                skillInstanceCreator == null ? "null" : skillInstanceCreator.getClass().toString());
    }

    public static SkillDefinition doNothing(int duration) {
        return new SkillDefinition(
                "do-nothing_" + duration + "_" + UUID.randomUUID().toString(),
                "Do Nothing",
                "Just sit there. Doing nothing. Like an idiot. For like " + duration + " units.",
                null, null,
                new DoNothingCreator(duration)
        );
    }
}
