package com.pedrorok.enx.home;

import com.destroystokyo.paper.ParticleBuilder;
import com.mojang.datafixers.kinds.IdF;
import com.pedrorok.enx.Main;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
@Getter
@AllArgsConstructor
public class HomeOptions {

    private final int cooldown;
    private final List<ParticleBuilder> particles;

    @Setter
    private boolean showParticles;
    private final boolean useSound;

    private final float volume;
    private final float pitch;
    private final Sound sound;

    public void teleportPlayer(Player player, Location location) {
        if (player.hasPermission("enx.home.bypass") || cooldown == 0) {
            tpPlayer(player, location);
            return;
        }
        player.sendMessage("§7[§fHomes§7] §eTeleportando em §f" + cooldown + " segundos§e...");
        player.getScheduler().runDelayed(Main.get(), (task) -> {
            tpPlayer(player, location);
        }, null, cooldown * 20L);
    }

    private void tpPlayer(Player player, Location location) {
        player.teleport(location);
        if (useSound) {
            player.playSound(location, sound, volume, pitch);
        }
        if (showParticles) {
            particles.forEach(particle -> {
                ParticleBuilder clone = particle.clone();
                clone.location(location);
                double x = clone.offsetX();
                double y = clone.offsetY();
                double z = clone.offsetZ();
                clone.offset(x, y + 0.5, z).spawn();
            });
        }
        player.sendMessage("§7[§fHomes§7] §eTeleportado com sucesso.");
    }
}
