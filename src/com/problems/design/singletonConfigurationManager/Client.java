package com.problems.design.singletonConfigurationManager;

public class Client {
    public static void main(String[] args) {
        ConfigManager configManager = ConfigManager.getInstance();
        ConfigManager configManager2 = ConfigManager.getInstance();

        System.out.println("App Name: " + configManager.getProperty("app.name"));
        System.out.println("DB URL: " + configManager2.getProperty("db.url"));

        // Prove singleton
        System.out.println("Both instances same ? " + (configManager == configManager2));
    }
}
