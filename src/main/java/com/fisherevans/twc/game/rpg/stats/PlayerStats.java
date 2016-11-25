package com.fisherevans.twc.game.rpg.stats;

import java.util.Comparator;

public interface PlayerStats extends Comparable<PlayerStats> {
    Comparator<PlayerStats> COMPARATOR = Comparator
            .comparing(PlayerStats::getAgility)
            .thenComparing(PlayerStats::getHealth);

    int getAgility();

    int getHealth();

    default int compareTo(PlayerStats other) {
        return COMPARATOR.compare(this, other);
    }

    static PlayerStats of(final int health, final int agility) {
        return new PlayerStats() {
            public int getAgility() { return agility; }
            public int getHealth() { return health; }
        };
    }

    default PlayerStats joinedWith(final PlayerStats other) {
        final PlayerStats current = this;
        return new PlayerStats() {
            public int getAgility() {
                return current.getAgility() + other.getAgility();
            }
            public int getHealth() {
                return current.getHealth() + other.getHealth();
            }
        };
    }
}
