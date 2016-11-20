package com.fisherevans.twc.game.rpg;

import java.util.Comparator;

public class PlayerStats implements Comparable<PlayerStats> {
    private static final Comparator<PlayerStats> COMPARATOR = Comparator
            .comparing(PlayerStats::getSpeed)
            .thenComparing(PlayerStats::getHealth);

    public int health;
    public int speed;

    public PlayerStats(int health, int speed) {
        this.health = health;
        this.speed = speed;
    }

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
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
