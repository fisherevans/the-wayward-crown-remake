package com.fisherevans.twc.game.rpg;

import java.util.Comparator;

public class PlayerStats implements Comparable<PlayerStats> {
    private static final Comparator<PlayerStats> COMPARATOR = Comparator
            .comparing(PlayerStats::getHealth);

    public int health;

    public PlayerStats(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    @Override
    public int compareTo(PlayerStats other) {
        return COMPARATOR.compare(this, other);
    }

    public String toString() {
        return String.format("PlayerStats[health=%d]",
                getHealth());
    }
}
