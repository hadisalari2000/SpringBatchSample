package com.examples.SpringBatchSample.utils;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.event.Level;
import org.springframework.stereotype.Component;


@Component
public class LogBackUtility {
    private static LogBackUtility logBackUtility;
    private static Logger logger;

    public static LogBackUtility getInstance(Logger log) {
        if (logBackUtility == null) {
            logBackUtility = new LogBackUtility();
            logger = log;
        }
        return logBackUtility;
    }

    public void log(Object o, Level level) {
        //ALL < TRACE < DEBUG < INFO < WARN < ERROR < FATAL < OFF
        String data = new Gson().toJson(o);
        switch (level) {
            case TRACE:
                logger.trace(data);
                break;
            case DEBUG:
                logger.debug(data);
                break;
            case INFO:
                logger.info(data);
                break;
            case WARN:
                logger.warn(data);
                break;
            case ERROR:
                logger.error(data);
                break;
        }
    }
}
