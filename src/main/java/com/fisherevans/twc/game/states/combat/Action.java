package com.fisherevans.twc.game.states.combat;

public class Action {
    private int duration;
    private String name;

    public Action(int duration, String name) {
        this.duration = duration;
        this.name = name;
    }

    public int getDuration() {
        return duration;
    }

    public String getName() {
        return name;
    }


}
