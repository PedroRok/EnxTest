package com.pedrorok.enx.home;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */

@Getter
public class PlayerHomes {

    private final String playerName;
    private final Map<String, Location> homes;

    public PlayerHomes(String playerName) {
        this.playerName = playerName;
        this.homes = new HashMap<>();
    }
    public PlayerHomes(String playerName, Map<String, Location> homes) {
        this.playerName = playerName;
        this.homes = homes;
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

    public boolean teleportToHome(Player player, String homeName) {
        return teleportToHome(player, getHome(homeName));
    }

    public boolean teleportToHome(Player player, Location location) {
        if (location == null) {
            return false;
        }
        if (location.getWorld() == null) {
            player.sendMessage("§cO mundo da home não existe.");
            return true;
        }
        player.teleport(location);
        player.sendMessage("§aTeleportado para a home.");
        return true;
    }

    public boolean removeHome(String homeName) {
        return homes.remove(homeName) != null;
    }


}
