package com.problems.design.singletonConfigurationManager;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigManager {

    private static volatile ConfigManager instance = null;
    private final Properties properties;

    private ConfigManager(){
        properties = new Properties();
        try(FileInputStream fis = new FileInputStream("config.properties")){
            properties.load(fis);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public static ConfigManager getInstance(){
        if(instance == null){
            synchronized (ConfigManager.class){
                if(instance == null){
                    instance = new ConfigManager();
                }
            }
        }
        return instance;
    }

    public String getProperty(String key){
        return properties.getProperty(key);
    }

}
