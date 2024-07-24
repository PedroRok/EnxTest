package com.pedrorok.enx.home;

import com.pedrorok.enx.Main;
import com.pedrorok.enx.utils.XConfig;
import com.pedrorok.enx.utils.XParticleBuilder;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 22/07/2024
 * @project EnxTest
 */
public class HomeConfig extends XConfig {

    // Classe responsável por carregar as configurações do plugin
    // relacionadas ao módulo Home

    private final HomeManager homeManager;

    public HomeConfig(HomeManager homeManager) {
        super("home-config.yml", Main.get());
        this.homeManager = homeManager;
    }

    @Override
    public void init() {

        int cooldown = config.getInt("home.cooldown");
        boolean showParticles = config.getBoolean("home.show-particles");
        boolean useSound = config.getBoolean("home.use-sounds");

        float volume = (float) config.getDouble("home.sound.volume");
        float pitch = (float) config.getDouble("home.sound.pitch");
        Sound sound = null;
        try {
            sound = Sound.valueOf(config.getString("home.sound.type"));
        } catch (NullPointerException e) {
            useSound = false;
            Main.LOGGER.error("Invalid sound type in home.sound.sound");
        }

        List<XParticleBuilder> particles = new ArrayList<>();
        for (String key : config.getSection("home.particles").getKeys(false)) {
            XParticleBuilder particleBuilder = config.getXParticleBuilder("home.particles." + key);
            if (particleBuilder == null) {
                useSound = false;
                break;
            }
            particles.add(particleBuilder);
        }
        homeManager.setHomeOptions(new HomeOptions(cooldown, particles, showParticles, useSound, volume, pitch, sound));
    }

    @Override
    public void save() {

    }

    @Override
    public void reload() {
        config.reloadConfig();
        init();
    }
}
