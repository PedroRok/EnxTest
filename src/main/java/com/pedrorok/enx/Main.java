package com.pedrorok.enx;

import com.pedrorok.enx.commands.CommandManager;
import com.pedrorok.enx.commands.HomeCommand;
import com.pedrorok.enx.commands.sub.HelpCmd;
import com.pedrorok.enx.commands.sub.ReloadCmd;
import com.pedrorok.enx.home.HomeManager;
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
    private HomeManager homeManager;
    private MainConfig mainConfig;

    public static final Logger LOGGER = LogManager.getLogger("EnxTest");
    @Override
    public void onEnable() {
        instance = this;

        // Iniciando o módulo do WindCharge
        windManager = new WindManager();
        homeManager = new HomeManager();
        mainConfig = new MainConfig(this);
        mainConfig.init();
        getServer().getPluginManager().registerEvents(new WindEvents(windManager), this);

        CommandManager enx = new CommandManager("enx", "§7[§dEnxTest§7] §r");
        enx.registerSubCommand("reload", new ReloadCmd());
        enx.registerSubCommand("help", new HelpCmd("enx"));
        getServer().getPluginCommand("enx").setExecutor(enx);

        getServer().getPluginCommand("home").setExecutor(new HomeCommand(homeManager));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        homeManager.close();
    }

    public static Main get() {
        return instance;
    }
}
