package com.fisherevans.twc.game.rpg;

import com.fisherevans.twc.util.imut_col.ImmutableList;

import java.util.List;
import java.util.Map;

public class SkillGroup {
    private final GraphNode rootNode;

    protected SkillGroup(GraphNode rootNode) {
        this.rootNode = rootNode;
    }

    public GraphNode getRootNode() {
        return rootNode;
    }

    public class GraphNode {
        protected final SkillDefinition skill;
        protected final List<GraphNode> followedBy;

        protected GraphNode(SkillDefinition skill, List<GraphNode> followedBy) {
            this.skill = skill;
            this.followedBy = followedBy;
        }

        public SkillDefinition getSkill() {
            return skill;
        }

        public ImmutableList<GraphNode> getFollowedBy() {
            return new ImmutableList<>(followedBy);
        }
    }
}
