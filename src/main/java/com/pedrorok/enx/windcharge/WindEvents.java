package com.pedrorok.enx.windcharge;

import org.bukkit.Location;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

/**
 * @author Rok, Pedro Lucas nmm. Created on 20/07/2024
 * @project EnxTest
 */
public class WindEvents implements Listener {

    private WindManager windManager;

    public WindEvents(WindManager windManager) {
        this.windManager = windManager;
    }

    @EventHandler
    public void onProjectileShoot(ProjectileLaunchEvent event) {
        if (!(event.getEntity().getShooter() instanceof WindCharge w)) return;
        if (!windManager.isUseCustomWind()) return;
        w.setVelocity(w.getVelocity().multiply(windManager.getWindOptions().getVelocity()));
    }

    @EventHandler
    public void onWindCharge(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof WindCharge)) return;
        if (!windManager.isUseCustomWind()) return;
        event.setCancelled(true);

        final Location location = event.getEntity().getLocation();
        windManager.getWindOptions().explode(location);
    }
}
