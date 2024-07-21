package com.pedrorok.enx;

import com.pedrorok.enx.commands.CommandManager;
import com.pedrorok.enx.windcharge.WindEvents;
import com.pedrorok.enx.windcharge.WindManager;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Rok, Pedro Lucas nmm. Created on 20/07/2024
 * @project EnxTest
 */
@Getter
public final class Main extends JavaPlugin {

    private static Main instance;
    private WindManager windManager;

    public static final Logger LOGGER = LogManager.getLogger("EnxTest");
    @Override
    public void onEnable() {
        instance = this;

        // Iniciando o módulo do WindCharge
        windManager = new WindManager();
        getServer().getPluginManager().registerEvents(new WindEvents(windManager), this);

        getServer().getPluginCommand("enx").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static Main get() {
        return instance;
    }
}
