package com.pedrorok.enx.home;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */

@Getter
public class PlayerHomes {

    // Classe responsável por armazenar as homes de um jogador

    private final String playerName;
    private final Map<String, Location> homes;

    public PlayerHomes(String playerName) {
        this.playerName = playerName;
        this.homes = new HashMap<>();
    }

    public void addHome(String homeName, Location location) {
        homes.put(homeName, location);
    }

    public void addHome(String homeName, double x, double y, double z, float yaw, float pitch, String world) {
        Location location = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
        homes.put(homeName, location);
    }

    public Location getHome(String homeName) {
        return homes.get(homeName);
    }

    public boolean removeHome(String homeName) {
        return homes.remove(homeName) != null;
    }


}
