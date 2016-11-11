package com.fisherevans.twc.slick;

import org.newdawn.slick.util.LogSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TWCLogSystem implements LogSystem {
    private static final Logger log = LoggerFactory.getLogger("Slick2D");

    @Override
    public void error(String s, Throwable throwable) {
        log.error(s, throwable);
    }

    @Override
    public void error(Throwable throwable) {
        log.error("", throwable);
    }

    @Override
    public void error(String s) {
        log.error(s);
    }

    @Override
    public void warn(String s) {
        log.warn(s);
    }

    @Override
    public void warn(String s, Throwable throwable) {
        log.warn(s, throwable);
    }

    @Override
    public void info(String s) {
        log.info(s);
    }

    @Override
    public void debug(String s) {
        log.debug(s);
    }
}
