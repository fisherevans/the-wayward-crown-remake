package com.fisherevans.twc.game.gfx.util;

import com.fisherevans.twc.game.states.splash.SplashState;
import com.fisherevans.twc.util.StringUtil;
import org.newdawn.slick.AngelCodeFont;
import org.newdawn.slick.Color;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;

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
        draw(color, 0, 0);
    }

    public void draw(Color color, float dx, float dy) {
        for(int id = 0;id < lines.length;id++) {
            font.drawString(xs[id] + dx, ys[id] + dy, lines[id], color);
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

        private static final String SPACE = " ";
        private static final String NEWLINE = "\n";

        public Builder(AngelCodeFont font, String text) {
            this.font = font;
            this.text = text;
        }

        public Builder aligned(AlignHorz alignHorz, AlignVert alignVert) {
            this.alignHorz = alignHorz;
            this.alignVert = alignVert;
            return this;
        }

        public Builder position(float x, float y) {
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
            final List<String> lineList = new LinkedList();
            final String[] textBreaks = text.split(NEWLINE);
            for(String textBreak:textBreaks) {
                if(wrapped) {
                    final String[] words = textBreak.split(SPACE);
                    String line = words.length == 0 ? "" : words[0]; // init with first
                    for (int id = 1; id < words.length; id++) { // start loop on second
                        final String word = words[id];
                        log.info(word);
                        final String projectedLine = line + SPACE + word;
                        log.info("  " + projectedLine);
                        if (font.getWidth(projectedLine) <= width) {
                            line = projectedLine;
                        } else { // break line, start a new one
                            lineList.add(line);
                            line = word;
                        }
                    }
                    if (line.length() > 0) {
                        lineList.add(line);
                    }
                } else {
                    lineList.add(textBreak);
                }
            }
            lines = lineList.toArray(new String[] { });
            log.info("building Text for: " + StringUtil.join(lines, ","));

            float baseY = 0;
            float lineDelta = font.getLineHeight()*lineHeight;
            switch (alignVert) {
                case TOP: baseY = y; break;
                //case MIDDLE: baseDy = (height/lineDelta - lines.length)*lineDelta/2f; break;
                case MIDDLE: baseY = y - Math.min(lineDelta*lines.length, height)/2f; break;
                case BOTTOM: baseY = y - Math.min(lineDelta*lines.length, height) + height; break;
            }
            final float[] ys = new float[lines.length];
            final float[] xs = new float[lines.length];
            for(int id = 0;id < lines.length;id++) {
                String line = lines[id];
                float dx = 0, dy = 0;
                switch(alignHorz) {
                    case LEFT: dx = 0; break;
                    case CENTER: dx = (width - font.getWidth(line))/2f; break;
                    case RIGHT: dx = width - font.getWidth(line); break;
                }
                switch(alignVert) {
                    case TOP: dy = 0; break;
                    case MIDDLE: dy = (height - font.getLineHeight())/2f; break;
                    case BOTTOM: dy = height - font.getLineHeight(); break;
                }
                xs[id] = x + dx;
                ys[id] = baseY + dy + (id*lineDelta);
            }
            return new Text(lines, xs, ys, font);
        }
    }

    public enum AlignHorz { LEFT, CENTER, RIGHT }
    public enum AlignVert { TOP, MIDDLE, BOTTOM }

}
