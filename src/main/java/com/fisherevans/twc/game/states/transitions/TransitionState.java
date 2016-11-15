package com.fisherevans.twc.game.states.transitions;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.input.Key;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import java.util.function.Function;

public class TransitionState implements TWCState {
    private final TWCState previous, next;
    private final float duration;
    private final Interpolation interpolation;
    private final Transition transition;

    private float elapsed = 0f;

    public TransitionState(TWCState previous, TWCState next, float duration, Interpolation interpolation, Transition transition) {
        this.previous = previous;
        this.next = next;
        this.duration = duration;
        this.interpolation = interpolation;
        this.transition = transition;
    }

    @Override
    public void init(TWCGame game) throws SlickException {

    }

    @Override
    public void enter(TWCGame game) throws SlickException {

    }

    @Override
    public void update(TWCGame game, float deltaSeconds) throws SlickException {
        elapsed += deltaSeconds;
        if(elapsed > duration) {
            game.setNextState(next);
        }
    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        transition.render(previous, next, game, graphics, interpolation.calc(duration, elapsed));
    }

    @Override
    public void keyPressed(TWCGame game, Key key, char c) {

    }

    @Override
    public void keyReleased(TWCGame game, Key key, char c) {

    }

    @Override
    public void leave(TWCGame game) throws SlickException {

    }

    public static TransitionState linearFade(TWCState previous, TWCState next, float duration) {
        return new TransitionState(previous, next, duration, Interpolation.linear(), Transition.simpleFade());
    }
}
