package com.pedrorok.enx;

import com.pedrorok.enx.utils.XConfig;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class MainConfig extends XConfig {

    private final Main main;
    public MainConfig(Main main) {
        super("config.yml", main);
        this.main = main;
    }

    @Override
    public void init() {
        loadWind();
        loadDatabase();
    }

    @Override
    public void save() {
    }

    @Override
    public void reload() {
        config.reloadConfig();
    }

    public void loadWind() {
        main.getWindManager().setUseCustomWind(config.getBoolean("custom-windcharge"));
    }

    public void loadHomes() {
        // TODO: import home config
    }

    public void loadDatabase() {
        String url = config.getString("database.host");
        String port = config.getString("database.port");
        String database = config.getString("database.database");
        String user = config.getString("database.username");
        String password = config.getString("database.password");

        main.getHomeManager().setHomeDatabase(url, port, database, user, password);
    }
}
