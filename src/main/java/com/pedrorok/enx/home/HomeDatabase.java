package com.pedrorok.enx.home;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.Location;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class HomeDatabase {

    // Classe responsável por realizar a conexão com o banco de dados e
    // realizar operações de CRUD com as tabelas de homes e players

    private HikariDataSource dataSource;

    public HomeDatabase(String dbUrl, String port, String dbName, String dbUser, String dbPassword) {
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("Driver JDBC do MariaDB não encontrado no classpath.");
            return;
        }
        try {
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl("jdbc:mariadb://" + dbUrl + ":" + port + "/" + dbName);
            config.setUsername(dbUser);
            if (dbPassword != null && !dbPassword.isEmpty()) {
                System.out.println("Password: " + dbPassword);
                config.setPassword(dbPassword);
            }

            dataSource = new HikariDataSource(config);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erro ao conectar ao banco de dados.");
        }
    }


    public void setupDatabase() {
        setupPlayersTable();
        setupHomesTable();
    }

    private void setupPlayersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS enx_players (" +
                "  playerUUID VARCHAR(36) PRIMARY KEY," +
                "  playerName VARCHAR(32)" +
                ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void setupHomesTable() {
        String sql = "CREATE TABLE IF NOT EXISTS enx_homes (" +
                "  id INT AUTO_INCREMENT PRIMARY KEY," +
                "  playerUUID VARCHAR(36) NOT NULL," +
                "  homeName VARCHAR(255) NOT NULL," +
                "  homeX DOUBLE NOT NULL," +
                "  homeY DOUBLE NOT NULL," +
                "  homeZ DOUBLE NOT NULL," +
                "  homeYaw FLOAT NOT NULL," +
                "  homePitch FLOAT NOT NULL," +
                "  homeWorld VARCHAR(255) NOT NULL," +
                "  UNIQUE KEY unique_home (playerUUID, homeName)," +
                "  FOREIGN KEY (playerUUID) REFERENCES enx_players(playerUUID) ON DELETE CASCADE" +
                ")";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayer(UUID playerUUID, String name) {
        String sql = "INSERT IGNORE INTO enx_players (playerUUID, playerName) VALUES (?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, name);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public UUID getPlayerByName(String playerName) {
        String sql = "SELECT playerUUID FROM enx_players WHERE playerName = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return UUID.fromString(resultSet.getString("playerUUID"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void savePlayerHome(UUID playerUUID, String home, Location location) {
        String sql = "INSERT INTO enx_homes (playerUUID, homeName, homeX, homeY, homeZ, homeYaw, homePitch, homeWorld) VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE homeX = VALUES(homeX)," +
                " homeY = VALUES(homeY)," +
                " homeZ = VALUES(homeZ)," +
                " homeYaw = VALUES(homeYaw)," +
                " homePitch = VALUES(homePitch)," +
                " homeWorld = VALUES(homeWorld)";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            if (location.getWorld() == null) {
                statement.cancel();
                return;
            }
            statement.setString(1, playerUUID.toString());
            statement.setString(2, home);
            statement.setDouble(3, location.getX());
            statement.setDouble(4, location.getY());
            statement.setDouble(5, location.getZ());
            statement.setFloat(6, location.getYaw());
            statement.setFloat(7, location.getPitch());
            statement.setString(8, location.getWorld().getName());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void removePlayerHome(UUID playerUUID, String home) {
        String sql = "DELETE FROM enx_homes WHERE playerUUID = ? AND homeName = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUUID.toString());
            statement.setString(2, home);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PlayerHomes getPlayerHomes(UUID playerUUID) {
        String sql = "SELECT homeName, homeX, homeY, homeZ, homeYaw, homePitch, homeWorld FROM enx_homes WHERE playerUUID = ?";

        PlayerHomes playerHomes = new PlayerHomes(playerUUID.toString());

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, playerUUID.toString());
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String homeName = resultSet.getString("homeName");
                    double homeX = resultSet.getDouble("homeX");
                    double homeY = resultSet.getDouble("homeY");
                    double homeZ = resultSet.getDouble("homeZ");
                    float homeYaw = resultSet.getFloat("homeYaw");
                    float homePitch = resultSet.getFloat("homePitch");
                    String homeWorld = resultSet.getString("homeWorld");
                    playerHomes.addHome(homeName, homeX, homeY, homeZ, homeYaw, homePitch, homeWorld);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playerHomes;
    }

    public void close() {
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
