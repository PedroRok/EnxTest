package com.pedrorok.enx.home;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class HomeManager {

    private final Map<UUID, PlayerHomes> homes;
    private HomeDatabase homeDatabase;

    public HomeManager() {
        this.homes = new HashMap<>();
    }

    public void setHomeDatabase(String url, String port, String database, String user, String password) {
        if (homeDatabase != null) {
            homeDatabase.close();
        }
        homeDatabase = new HomeDatabase(url, port, database, user, password);
        homeDatabase.setupDatabase();
    }

    public void savePlayerHome(Player player, String homeName, Location location) {
        if (homeDatabase == null) {
            return;
        }
        UUID uuid = player.getUniqueId();
        String playerName = player.getName();
        homeDatabase.addPlayer(uuid, playerName);
        homeDatabase.savePlayerHome(uuid, homeName, location);
        homes.get(uuid).addHome(homeName, location);
    }

    public boolean removePlayerHome(UUID uuid, String homeName) {
        if (homeDatabase == null) {
            return false;
        }
        homeDatabase.removePlayerHome(uuid, homeName);
        return homes.get(uuid).removeHome(homeName);
    }

    public Location getPlayerHome(UUID uuid, String homeName) {
        return getPlayerHomes(uuid).getHome(homeName);
    }

    public PlayerHomes getPlayerHomes(UUID uuid) {
        if (homes.containsKey(uuid)) {
            return homes.get(uuid);
        }
        return loadPlayerHomes(uuid);
    }

    public PlayerHomes loadPlayerHomes(UUID uuid) {
        if (homeDatabase == null) {
            return null;
        }
        PlayerHomes playerHomes = homeDatabase.getPlayerHomes(uuid);
        homes.put(uuid, playerHomes);
        return playerHomes;
    }

    public PlayerHomes getPlayerByName(String playerName) {
        for (Map.Entry<UUID, PlayerHomes> entry : homes.entrySet()) {
            if (entry.getValue().getPlayerName().equalsIgnoreCase(playerName)) {
                return entry.getValue();
            }
        }
        if (homeDatabase == null) {
            return null;
        }
        UUID uuid = homeDatabase.getPlayerByName(playerName);
        if (uuid == null) {
            return null;
        }
        PlayerHomes playerHomes = homeDatabase.getPlayerHomes(uuid);
        return playerHomes;
    }

    public void close() {
        if (homeDatabase == null) return;
        homeDatabase.close();
    }


    public void teleportPlayer(Player player, Location location) {
        // Teleport the player to the location
        player.teleport(location);
    }

}
