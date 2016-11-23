package com.fisherevans.twc.game.rpg.skills;

import com.fisherevans.twc.game.rpg.skills.SkillGroup.GraphNode;
import com.fisherevans.twc.util.imut_col.ImmutableSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SkillGroupRegistry {
    private static final Logger log = LoggerFactory.getLogger(SkillRegistry.class);

    private final Map<String, SkillGroup> groups;

    private SkillGroupRegistry(Map<String, SkillGroup> groups) {
        this.groups = groups;
    }

    public SkillGroup get(String id) {
        return groups.get(id);
    }

    public Set<String> getIds() {
        return new ImmutableSet(groups.keySet());
    }

    public static SkillGroupRegistry loadFromYamlFile(String path, SkillRegistry skills) throws IOException {
        return loadFromYaml(SkillRegistry.class.getResource(path), skills);
    }

    public static SkillGroupRegistry loadFromYaml(URL url, SkillRegistry skills) throws IOException {
        InputStream is = null;
        try {
            log.info(String.valueOf(url));
            is = url.openStream();
            Yaml yaml = new Yaml();
            Map config = (Map) yaml.load(is);
            log.info("Got config: " + String.valueOf(config));

            Map groupsConfig = (Map) config.get("groups");
            Map<SkillDefinition, GraphNode> nodes = baseNodes(skills);
            for (Object key : groupsConfig.keySet()) {
                GraphNode node = nodes.get(skills.get((String) key));
                for(String followedById:(List<String>) groupsConfig.get(key)) {
                    node.followedBy.add(nodes.get(skills.get(followedById)));
                }
            }

            List<String> rootIds = (List<String>) config.get("roots");
            final Map<String, SkillGroup> groups = new HashMap();
            for(String rootId:rootIds) {
                SkillDefinition skill = skills.get(rootId);
                groups.put(rootId, new SkillGroup(nodes.get(skill)));
            }

            return new SkillGroupRegistry(groups);
        } finally {
            if(is != null) {
                is.close();
            }
        }
    }

    private static Map<SkillDefinition, GraphNode> baseNodes(SkillRegistry skills) {
        Map<SkillDefinition, GraphNode> nodes = new HashMap();
        for(String id:skills.ids()) {
            SkillDefinition skill = skills.get(id);
            nodes.put(skill, new GraphNode(skill, new HashSet()));
        }
        return nodes;
    }
}
