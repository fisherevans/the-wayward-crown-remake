package com.fisherevans.twc.game.gfx;

import com.fisherevans.twc.game.states.splash.SplashState;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by immortal on 11/10/2016.
 */
public class Text {
    private static final Logger log = LoggerFactory.getLogger(SplashState.class);

    private final String[] lines;
    private final float[] xs, ys;
    private final AngelCodeFont font;

    public Text(String[] lines, float[] xs, float[] ys, AngelCodeFont font) {
        this.lines = lines;
        this.xs = xs;
        this.ys = ys;
        this.font = font;
    }

    public void draw(Color color) {
        for(int id = 0;id < lines.length;id++) {
            font.drawString(xs[id], ys[id], lines[id], color);
        }
    }

    public static class Builder {
        private final AngelCodeFont font;
        private final String text;

        private AlignHorz alignHorz = AlignHorz.CENTER;
        private AlignVert alignVert = AlignVert.MIDDLE;
        private float x = 0, y = 0;
        private boolean wrapped = false;
        private float width = 0, height = 0;
        private float lineHeight = 2f;

        public Builder(AngelCodeFont font, String text) {
            this.font = font;
            this.text = text;
        }

        public Builder aligned(AlignHorz alignHorz, AlignVert alignVert) {
            this.alignHorz = alignHorz;
            this.alignVert = alignVert;
            return this;
        }

        public Builder topLeftPosition(float x, float y) {
            this.x = x;
            this.y = y;
            return this;
        }

        public Builder wrapped(boolean wrapped) {
            this.wrapped = wrapped;
            return this;
        }

        public Builder size(float width, float height) {
            this.width = width;
            this.height = height;
            return this;
        }

        public Builder lineHeight(float lineHeight) {
            this.lineHeight = lineHeight;
            return this;
        }

        public Text build() {
            final String[] lines;
            if(wrapped) {
                final List<String> lineList = new LinkedList();
                final String[] words = text.split(" ");
                String line = words.length == 0 ? "" : words[0]; // init with first
                for(int id = 1;id < words.length;id++) { // start loop on second
                    final String word = words[id];
                    final String projectedLine = line + " " + word;
                    if(font.getWidth(projectedLine) <= width) {
                        line = projectedLine;
                    } else { // break line, start a new one
                        lineList.add(line);
                        line = word;
                    }
                }
                if(line.length() > 0) {
                    lineList.add(line);
                }
                lines = lineList.toArray(new String[] { });
            } else {
                lines = new String[] { text };
            }

            float baseDy = 0;
            float lineDelta = font.getLineHeight()*lineHeight;
            switch (alignVert) {
                case TOP: baseDy = 0; break;
                case MIDDLE: baseDy = (height/lineHeight - lines.length)*lineDelta/2f; break;
                case BOTTOM: baseDy = (height/lineHeight - lines.length)*lineDelta; break;
            }
            final float[] ys = new float[lines.length];
            final float[] xs = new float[lines.length];
            for(int id = 0;id < lines.length;id++) {
                String line = lines[id];
                float lineX = x;
                float lineY = y+(id*lineDelta)+baseDy;
                float dx = 0, dy = 0;
                switch(alignHorz) {
                    case LEFT: dx = 0; break;
                    case CENTER: dx = (width - font.getWidth(line))/2f; break;
                    case RIGHT: dx = width - font.getWidth(line); break;
                }
                switch(alignVert) {
                    case TOP: dy = 0; break;
                    case MIDDLE: dy = (height - font.getHeight("0"))/2f; break;
                    case BOTTOM: dy = height - font.getHeight("0"); break;
                }
                xs[id] = lineX + dx;
                ys[id] = lineY + dy;
            }
            return new Text(lines, xs, ys, font);
        }
    }

    public enum AlignHorz { LEFT, CENTER, RIGHT }
    public enum AlignVert { TOP, MIDDLE, BOTTOM }

}
