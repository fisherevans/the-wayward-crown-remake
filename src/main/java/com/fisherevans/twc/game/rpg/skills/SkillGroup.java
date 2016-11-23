package com.fisherevans.twc.game.rpg.skills;

import com.fisherevans.twc.util.imut_col.ImmutableSet;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SkillGroup {
    private final GraphNode rootNode;

    protected SkillGroup(GraphNode rootNode) {
        this.rootNode = rootNode;
    }

    public GraphNode getRootNode() {
        return rootNode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillGroup that = (SkillGroup) o;
        return rootNode != null ? rootNode.equals(that.rootNode) : that.rootNode == null;

    }

    @Override
    public int hashCode() {
        return rootNode != null ? rootNode.hashCode() : 0;
    }

    @Override
    public String toString() {
        return String.format("SkillGroup[root=%s]", String.valueOf(rootNode));
    }

    public static class GraphNode {
        protected final SkillDefinition skill;
        protected final Set<GraphNode> followedBy;

        public GraphNode(SkillDefinition skill) {
            this.skill = skill;
            this.followedBy = new HashSet();
        }

        protected GraphNode(SkillDefinition skill, Set<GraphNode> followedBy) {
            this.skill = skill;
            this.followedBy = followedBy;
        }

        public SkillDefinition getSkill() {
            return skill;
        }

        public ImmutableSet<GraphNode> getFollowedBy() {
            return new ImmutableSet<>(followedBy);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GraphNode graphNode = (GraphNode) o;
            return skill != null ? skill.equals(graphNode.skill) : graphNode.skill == null;

        }

        @Override
        public int hashCode() {
            return skill != null ? skill.hashCode() : 0;
        }

        @Override
        public String toString() {
            return String.format("GraphNode[skill=%s,followedBy=%s]",
                    String.valueOf(skill.getId()),
                    String.valueOf(followedBy
                            .stream()
                            .map(GraphNode::getSkill)
                            .map(SkillDefinition::getId)
                            .collect(Collectors.toList())));
        }
    }
}
