package com.fisherevans.twc.game.states.combat.skills;

import com.fisherevans.twc.game.states.combat.CombatEnvironment;
import com.fisherevans.twc.game.states.combat.CombatPlayer;

public class SegmentNotification {
    private final CombatEnvironment combatEnvironment;
    private final NotificationType eventType;
    private final CombatPlayer owner;
    private final CombatPlayer opponent;

    public SegmentNotification(CombatEnvironment combatEnvironment, NotificationType eventType, CombatPlayer owner, CombatPlayer opponent) {
        this.combatEnvironment = combatEnvironment;
        this.eventType = eventType;
        this.owner = owner;
        this.opponent = opponent;
    }

    public CombatEnvironment getCombatEnvironment() {
        return combatEnvironment;
    }

    public NotificationType getEventType() {
        return eventType;
    }

    public CombatPlayer getOwner() {
        return owner;
    }

    public CombatPlayer getOpponent() {
        return opponent;
    }

    public enum NotificationType { START, INTERRUPT, END }

    @Override
    public String toString() {
        return String.format("SegmentNotification[type=%s,owner=%s,opponent=%s]",
                eventType.name(),
                owner.getName(),
                opponent.getName());
    }
}
