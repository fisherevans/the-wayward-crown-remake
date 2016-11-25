package com.fisherevans.twc.game.rpg.stats;

import java.util.HashMap;
import java.util.Map;

public class StatsModifier {
    private final Map<Type, Map<Object, Integer>> mods;

    public StatsModifier() {
        mods = new HashMap<>();
        for(Type type:Type.values()) {
            mods.put(type, new HashMap<>());
        }
    }

    public int addModifier(Type type, Object id, int amount) {
        Integer mod = mods.get(type).put(id, amount);
        return mod == null ? 0 : mod;
    }

    public int removeModifer(Type type, Object id) {
        Integer mod = mods.get(type).remove(id);
        return mod == null ? 0 : mod;
    }

    public int getModifer(Type type, Object id) {
        Integer mod = mods.get(type).get(id);
        return mod == null ? 0 : mod;
    }

    public int getTotalModifier(Type type) {
        return mods.get(type).values().stream().reduce(0, (a, b) -> a + b);
    }

    public PlayerStats asPlayerStats() {
        return new PlayerStats() {
            public int getAgility() {
                return getTotalModifier(Type.AGILITY);
            }
            public int getHealth() {
                return getTotalModifier(Type.HEALTH);
            }
        };
    }

    public enum Type { HEALTH, AGILITY }
}
