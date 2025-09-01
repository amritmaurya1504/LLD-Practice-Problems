package com.problems.design.singletonLogger;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
    public static void main(String[] args) {
        System.out.println("Singleton Logger Class!");
        /*Logger log = Logger.getLoggerInstance();

        System.out.println("Client class logger hashcode: " + log.hashCode());

        Service.doSomething();*/

        /*log.info("Size of list: 10");
        log.error("An error occurred while opening file! ");
        log.debug("We need to debug this program");*/


        // Let's Break Singleton Pattern
        Logger log1 = Logger.getLoggerInstance();
        try {
            Constructor<Logger> constructor = Logger.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            Logger log2 = constructor.newInstance();
            System.out.println(log1 == log2);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }


    }
}
