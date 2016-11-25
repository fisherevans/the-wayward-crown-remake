package com.fisherevans.twc.game.rpg.skills;

import com.fisherevans.twc.game.states.combat.skills.SkillCombatHandler;
import com.fisherevans.twc.game.states.combat.skills.creators.DoNothingCreator;
import com.fisherevans.twc.game.states.combat.skills.creators.SkillCombatHandlerCreator;
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

    private final SkillCombatHandler combatHandler;

    public SkillDefinition(String id, String name, String description, Image smallIcon, Image largeIcon, SkillCombatHandlerCreator creator) {
        this(id, name, description, smallIcon, largeIcon, creator.create());
    }

    public SkillDefinition(String id, String name, String description, Image smallIcon, Image largeIcon, SkillCombatHandler combatHandler) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.smallIcon = smallIcon;
        this.largeIcon = largeIcon;
        this.combatHandler = combatHandler;
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

    public SkillCombatHandler getCombatHandler() {
        return combatHandler;
    }

    @Override
    public String toString() {
        return String.format("SkillDefinition[id=%s,name='%s',desc='%s',sIcon=%s,lIcon=%s,instC=%s]",
                id,
                name,
                description,
                String.valueOf(smallIcon),
                String.valueOf(largeIcon),
                combatHandler == null ? "null" : combatHandler.getClass().toString());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillDefinition that = (SkillDefinition) o;
        return id != null ? id.equals(that.id) : that.id == null;

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }

    public static SkillDefinition doNothing(int duration) {
        return new SkillDefinition(
                "do-nothing_" + duration + "_" + UUID.randomUUID().toString(),
                "Do Nothing (" + duration + ")",
                "Just sit there. Doing nothing. Like an idiot. For like " + duration + " units.",
                null, null,
                new DoNothingCreator(duration)
        );
    }
}
