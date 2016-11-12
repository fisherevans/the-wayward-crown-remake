package com.fisherevans.twc.game.states.transitions;

public interface Interpolation {
    float calc(float duration, float elapsed);

    static Interpolation linear() {
        return (duration, elapsed) -> elapsed/duration;
    }
}
