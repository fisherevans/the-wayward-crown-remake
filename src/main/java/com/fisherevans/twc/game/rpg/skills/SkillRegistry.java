package com.fisherevans.twc.game.rpg.skills;

import com.fisherevans.twc.game.states.combat.skills.creators.SkillCombatHandlerCreator;
import com.fisherevans.twc.util.imut_col.ImmutableSet;
import org.newdawn.slick.Image;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SkillRegistry {
    private static final Logger log = LoggerFactory.getLogger(SkillRegistry.class);

    private final Map<String, SkillDefinition> skills;

    private SkillRegistry(Map<String, SkillDefinition> skills) {
        this.skills = skills;
    }

    public SkillDefinition get(String id) {
        SkillDefinition result = skills.get(id);
        if(result == null) {
            throw new RuntimeException("Unknown Skill: " + id);
        }
        return result;
    }

    public ImmutableSet<String> ids() {
        return new ImmutableSet(skills.keySet());
    }

    public static SkillRegistry loadFromYamlFile(String path) throws IOException {
        return loadFromYaml(SkillRegistry.class.getResource(path));
    }

    public static SkillRegistry loadFromYaml(URL url) throws IOException {
        InputStream is = url.openStream();
        try {
            log.info(String.valueOf(url));
            Map<String, SkillDefinition> skills = new HashMap();
            SkillsFile file = new Yaml().loadAs(is, SkillsFile.class);
            for(String skillId:file.skills.keySet()) {
                SkillYaml skillYaml = file.skills.get(skillId);
                SkillDefinition skill = new SkillDefinition(
                        skillId,
                        skillYaml.name,
                        skillYaml.description,
                        getImage(skillYaml.icons.small),
                        getImage(skillYaml.icons.large),
                        getCreator(
                                skillYaml.combatHandler.creator,
                                skillYaml.combatHandler.config
                        )
                );
                skills.put(skillId, skill);
            }
            return new SkillRegistry(skills);
        } finally {
            if(is != null) {
                is.close();
            }
        }
    }

    private static SkillCombatHandlerCreator getCreator(String path, Object args) {
        try {
            Class clazz = Class.forName(path);
            return (SkillCombatHandlerCreator) (args == null
                    ? clazz.getConstructor().newInstance()
                    : clazz.getConstructor(Object.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load skill instance creator", e);
        }
    }

    private static Image getImage(String path) {
        try {
            return new Image(path, false, Image.FILTER_NEAREST);
        } catch (Exception e) {
            throw new RuntimeException("Failed to load image for :" + path, e);
        }
    }

    public static class SkillsFile {
        public Map<String, SkillYaml> skills;
        public SkillsFile() { }
    }

    public static class SkillYaml {
        public String name, description;
        public SkillIcons icons;
        public SkillCombatDef combatHandler;
        public SkillYaml() { }
    }

    public static class SkillIcons {
        public String small, large;
        SkillIcons() { }
    }

    public static class SkillCombatDef {
        public String creator;
        public Object config;
        public SkillCombatDef() { }
    }
}
