package com.fisherevans.twc.game.states.combat.players.skills;

import com.fisherevans.twc.game.states.combat.players.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface SegmentListener {
    Logger log = LoggerFactory.getLogger(SegmentListener.class);

    interface Start {
        Start empty = (owner, skill, segment, opponents) ->
                Arrays.asList(() -> log.info(String.format(
                        "Segment Started. (%s, %s, %s, %s)",
                        String.valueOf(owner),
                        String.valueOf(skill),
                        String.valueOf(segment),
                        String.valueOf(opponents)

                )));
        List<Action> start(Player owner, Skill skill, SkillSegment segment, Set<Player> opponents);
    }

    interface Interrupt {
        Interrupt empty = (owner, ownerSkill, ownerSegment, opponent, opponentSkill, opponentSegment) ->
                Arrays.asList(() -> log.info(String.format(
                        "Segment Interrupted. (%s, %s, %s, %s)",
                        String.valueOf(owner),
                        String.valueOf(ownerSkill),
                        String.valueOf(ownerSegment),
                        String.valueOf(opponent),
                        String.valueOf(opponentSkill),
                        String.valueOf(opponentSegment)

                )));
        List<Action> interrupt(Player owner, Skill ownerSkill, SkillSegment ownerSegment, Player opponent, Skill opponentSkill, SkillSegment opponentSegment);
    }

    interface End {
        End empty = (owner, skill, segment, opponents) ->
                Arrays.asList(() -> log.info(String.format(
                        "Segment Ended. (%s, %s, %s, %s)",
                        String.valueOf(owner),
                        String.valueOf(skill),
                        String.valueOf(segment),
                        String.valueOf(opponents)

                )));
        List<Action> end(Player owner, Skill skill, SkillSegment segment, Set<Player> opponents);
    }
}
