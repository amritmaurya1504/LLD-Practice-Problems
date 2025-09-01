package com.problems.design.singletonLogger;

public class Service {
    public static void doSomething() {
        Logger log2 = Logger.getLoggerInstance();
        log2.debug("Service: Performing some operation...");

        // Check if same instance
        System.out.println("Logger hashCode from Service: " + log2.hashCode());
    }
}
