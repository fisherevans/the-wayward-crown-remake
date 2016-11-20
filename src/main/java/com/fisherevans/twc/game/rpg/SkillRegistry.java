package com.fisherevans.twc.game.rpg;

import com.fisherevans.twc.game.states.combat.skills.creators.SkillInstanceCreator;
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

    public static SkillRegistry loadFromYaml(URL url) throws IOException {
        InputStream is = null;
        try {
            log.info(String.valueOf(url));
            is = url.openStream();
            Yaml yaml = new Yaml();
            Map<String, SkillDefinition> skills = new HashMap();
            Map config = (Map) yaml.load(is);
            for (Object key : config.keySet()) {
                Map value = (Map) config.get(key);
                SkillDefinition skill = new SkillDefinition(
                        (String) key,
                        (String) value.get("name"),
                        (String) value.get("desc"),
                        getImage((String) value.get("smallIcon")),
                        getImage((String) value.get("largeIcon")),
                        getCreator(
                                (String) value.get("instanceCreator"),
                                value.get("instanceCreatorArgs")
                        )
                );
                skills.put(skill.getId(), skill);
            }
            return new SkillRegistry(skills);
        } finally {
            if(is != null) {
                is.close();
            }
        }
    }

    private static SkillInstanceCreator getCreator(String path, Object args) {
        try {
            Class clazz = Class.forName(path);
            return (SkillInstanceCreator) (args == null
                    ? clazz.getConstructor().newInstance()
                    : clazz.getConstructor(Object.class).newInstance(args));
        } catch (Exception e) {
            throw new RuntimeException("Failed to load skill instance creator", e);
        }
    }

    private static Image getImage(String path) {
        // TODO
        return null;
    }

    public static void main(String[] args) throws IOException {
        SkillRegistry registry = loadFromYaml(SkillRegistry.class.getResource("/skills/defs.yml"));
        for(String id:registry.skills.keySet()) {
            log.info(String.format("%20s > %s",
                    id, String.valueOf(registry.skills.get(id))));
        }
    }
}
