package com.ttknpdev.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Logback {
    public Logger log;
    public Logback(Class c) {
        log = LoggerFactory.getLogger(c);
    }
}
