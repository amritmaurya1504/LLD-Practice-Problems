package com.problems.design.singletonLogger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {

    private static volatile Logger loggerInstance = null;
    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern(
            "yyyy-mm-dd HH:mm:ss"
    );
    public enum LogLevel {
        INFO, ERROR, DEBUG
    }

    private Logger(){
        if (loggerInstance != null) {
            throw new RuntimeException("Use getInstance() only!");
        }else{
            System.out.println("Logger created by: " + Thread.currentThread().getName());
        }
    }

    public static Logger getLoggerInstance() {
        if(loggerInstance == null){
            synchronized (Logger.class) {
                if(loggerInstance == null){
                    loggerInstance = new Logger();
                }
            }
        }
        return loggerInstance;
    }

    public void log(LogLevel logLevel, String message){
        String timestamp = LocalDateTime.now().format(dtFormatter);
        System.out.println("[" + timestamp + "] [" + logLevel + "] " + message);
    }

    public void info(String message){
        log(LogLevel.INFO, message);
    }

    public void debug(String message) {
        log(LogLevel.DEBUG, message);
    }

    public void error(String message) {
        log(LogLevel.ERROR, message);
    }

}
