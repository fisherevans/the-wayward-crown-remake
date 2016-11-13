package com.fisherevans.twc.game.input;

import static org.newdawn.slick.Input.*;

import java.util.HashMap;
import java.util.Map;

public enum Key {
    MENU,

    UP, DOWN, LEFT, RIGHT,

    BACK, SELECT,

    OPTION_1,
    OPTION_2,
    OPTION_3,
    OPTION_4,
    OPTION_5,
    OPTION_6,
    OPTION_7,
    OPTION_8,
    OPTION_9,
    OPTION_0,
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
        map.put(KEY_1, OPTION_1);
        map.put(KEY_2, OPTION_2);
        map.put(KEY_3, OPTION_3);
        map.put(KEY_4, OPTION_4);
        map.put(KEY_5, OPTION_5);
        map.put(KEY_6, OPTION_6);
        map.put(KEY_7, OPTION_7);
        map.put(KEY_8, OPTION_8);
        map.put(KEY_9, OPTION_9);
        map.put(KEY_0, OPTION_0);
        return map;
    }
}
