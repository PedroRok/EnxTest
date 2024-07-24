package com.pedrorok.enx.commands.sub;

import com.pedrorok.enx.Main;
import com.pedrorok.enx.commands.SubCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 20/07/2024
 * @project EnxTest
 */
public class ReloadCmd extends SubCommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length != 1) return true;

        String module = args[0].toLowerCase();

        switch (module) {
            case "wind":
                sendMsg(sender, "§aRecarregando  configurações do WindCharge!");
                Main.get().getMainConfig().reload();
                Main.get().getMainConfig().loadWind();
                Main.get().getWindManager().getWindConfig().reload();
                return true;
            case "database":
                sendMsg(sender, "§aRecarregando banco de dados!");
                Main.get().getMainConfig().reload();
                Main.get().getMainConfig().loadDatabase();
                return true;
            case "homes":
                sendMsg(sender, "§aRecarregando configurações de Home!");
                Main.get().getHomeManager().getHomeConfig().reload();
                return true;
            default:
                sendMsg(sender, "§cMódulo não encontrado.");
                return true;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length != 0) return null;
        return List.of("wind", "database", "homes");
    }

    @Override
    public String getUsage() {
        return "reload <wind|database|homes>";
    }

    @Override
    public String getPermission() {
        return "enx.admin";
    }

    @Override
    public String getDescription() {
        return "Recarrega a configuração do plugin.";
    }
}
