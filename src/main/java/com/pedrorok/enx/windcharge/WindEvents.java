package com.pedrorok.enx.windcharge;

import org.bukkit.Location;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

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
    public void onWindCharge(EntityExplodeEvent event) {
        if (!(event.getEntity() instanceof WindCharge)) return;
        if (!windManager.isUseCustomWind()) return;
        event.setCancelled(true);

        final Location location = event.getEntity().getLocation();
        windManager.getWindOptions().explode(location);
    }
}
