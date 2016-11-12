package com.fisherevans.twc.game.input;

import static org.newdawn.slick.Input.*;

import java.util.HashMap;
import java.util.Map;

public enum Key {
    MENU,

    UP, DOWN, LEFT, RIGHT,

    BACK, SELECT
    ;

    public static Map<Integer, Key> defaultKeyMap() {
        Map<Integer, Key> map = new HashMap();
        map.put(KEY_ESCAPE, MENU);
        map.put(KEY_W, UP);
        map.put(KEY_S, DOWN);
        map.put(KEY_A, LEFT);
        map.put(KEY_D, RIGHT);
        map.put(KEY_SPACE, SELECT);
        map.put(KEY_BACK, BACK);
        return map;
    }
}
