package com.fisherevans.twc.game.states.splash;

import com.fisherevans.twc.game.TWCGame;
import com.fisherevans.twc.game.TWCState;
import com.fisherevans.twc.game.gfx.resources.Fonts;
import com.fisherevans.twc.game.gfx.util.Text;
import com.fisherevans.twc.game.gfx.util.Text.AlignHorz;
import com.fisherevans.twc.game.gfx.util.Text.AlignVert;
import com.fisherevans.twc.game.input.Key;
import com.fisherevans.twc.game.states.combat.CombatState;
import com.fisherevans.twc.game.states.transitions.TransitionState;
import com.fisherevans.twc.util.MathUtil;
import org.newdawn.slick.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.fisherevans.twc.util.MathUtil.PI;

public class SplashState implements TWCState {
    private static final Logger log = LoggerFactory.getLogger(SplashState.class);

    private float flashRadians = 0f, fadeIn = 0;

    private AngelCodeFont bigFont, smallFont;
    private Text titleText, flashText;

    @Override
    public void init(TWCGame game) throws SlickException {
        bigFont = Fonts.DEFAULT_4.load();
        smallFont = Fonts.DEFAULT_1.load();
        titleText = new Text.Builder(bigFont, "The Wayward\nCrown")
                .aligned(AlignHorz.CENTER, AlignVert.BOTTOM)
                .position(game.getRenderContext().width/2f, game.getRenderContext().height*0.6f)
                .lineHeight(1.15f)
                .build();
        flashText = new Text.Builder(smallFont, ">  Press any key...  <")
                .aligned(AlignHorz.CENTER, AlignVert.TOP)
                .position(game.getRenderContext().width/2f, game.getRenderContext().height*0.65f)
                .build();
    }

    @Override
    public void enter(TWCGame game) throws SlickException {

    }

    @Override
    public void update(TWCGame game, float deltaSeconds) throws SlickException {
        if(fadeIn < 1) {
            fadeIn += deltaSeconds*0.3333f;
            if(fadeIn > 1) {
                fadeIn = 1;
            }
        }
        flashRadians = (flashRadians + (deltaSeconds*PI)) % (PI*2f);
    }

    @Override
    public void render(TWCGame game, Graphics graphics) throws SlickException {
        final float flash = ((MathUtil.sin(flashRadians) + 1f)/4f + 0.25f)*fadeIn;
        titleText.draw(new Color(fadeIn, fadeIn, fadeIn));
        flashText.draw(new Color(flash, flash, flash));
    }

    @Override
    public void keyPressed(TWCGame game, Key key, char c) throws SlickException {
        CombatState combatState = new CombatState();
        combatState.init(game);
        TransitionState transitionState = TransitionState.linearFade(this, combatState, 1.5f);
        transitionState.init(game);
        game.setNextState(transitionState);
    }

    @Override
    public void keyReleased(TWCGame game, Key key, char c) {

    }

    @Override
    public void leave(TWCGame game) throws SlickException {

    }
}
