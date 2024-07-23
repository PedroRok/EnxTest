package com.pedrorok.enx.windcharge;

import com.destroystokyo.paper.ParticleBuilder;
import com.pedrorok.enx.Main;
import com.pedrorok.enx.utils.XConfig;
import org.bukkit.Sound;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class WindConfig extends XConfig {

    private WindManager windManager;

    public WindConfig(WindManager windManager) {
        super("wind-config.yml", Main.get());
        this.windManager = windManager;
    }

    @Override
    public void init() {
        // Importando os valores do WindCharge
        double power = config.getDouble("windcharge.power");
        double size = config.getDouble("windcharge.size");

        // Importando os valores do som
        float volume = (float) config.getDouble("windcharge.sound.volume");
        float pitch = (float) config.getDouble("windcharge.sound.pitch");
        Sound sound;
        try {
            sound = Sound.valueOf(config.getString("windcharge.sound.type"));
        } catch (NullPointerException e) {
            windManager.setUseCustomWind(false);
            Main.LOGGER.error("Invalid sound type in windcharge.sound.sound");
            return;
        }

        // Criando as opções do Charge
        WindOptions windOptions = new WindOptions(power, size, volume, pitch, sound);

        // Importando as partículas
        for (String key : config.getSection("windcharge.particles").getKeys(false)) {
            ParticleBuilder particleBuilder = config.getParticleBuilder("windcharge.particles." + key);
            if (particleBuilder == null) {
                windManager.setUseCustomWind(false);
                return;
            }
            windOptions.addParticle(particleBuilder);
        }
        windManager.setWindOptions(windOptions);
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
