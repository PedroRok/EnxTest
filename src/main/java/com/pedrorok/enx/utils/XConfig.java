package com.pedrorok.enx.utils;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Rok, Pedro Lucas nmm. Created on 29/12/2023
 * @project ArtequeGames
 */
public abstract class XConfig {

    protected final YamlConfig config;
    public XConfig(String fileName, JavaPlugin main) {
        config = new YamlConfig(fileName, main);
    }
    public abstract void init();

    public abstract void save();

    public abstract void reload();
}
