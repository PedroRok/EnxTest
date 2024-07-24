package com.pedrorok.enx;

import com.pedrorok.enx.commands.CommandManager;
import com.pedrorok.enx.commands.sub.HelpCmd;
import com.pedrorok.enx.commands.sub.ReloadCmd;
import com.pedrorok.enx.home.HomeManager;
import com.pedrorok.enx.windcharge.WindManager;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.plugin.java.JavaPlugin;

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
        windManager = new WindManager(this);

        // Iniciando o módulo do Home
        homeManager = new HomeManager(this);

        // Importando configurações essenciais
        mainConfig = new MainConfig(this);
        mainConfig.init();

        // Registrando comando principal
        CommandManager enx = new CommandManager("enx", "§7[§dEnxTest§7] §r");
        enx.registerSubCommand("reload", new ReloadCmd());
        enx.registerSubCommand("help", new HelpCmd("enx"));
        getServer().getPluginCommand("enx").setExecutor(enx);
    }

    @Override
    public void onDisable() {
        // Fechando conexão com o banco de dados
        homeManager.close();
    }

    public static Main get() {
        return instance;
    }
}
