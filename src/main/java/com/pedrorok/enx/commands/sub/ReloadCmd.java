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
                Main.get().getMainConfig().reload();
                Main.get().getMainConfig().loadWind();
                Main.get().getWindManager().getWindConfig().reload();
                sendMsg(sender, "§aConfiguração recarregada com sucesso!");
                return true;
            case "database":
                Main.get().getMainConfig().reload();
                Main.get().getMainConfig().loadDatabase();
                sendMsg(sender, "§aMensagens recarregadas com sucesso!");
                return true;
            case "homes":
                Main.get().getHomeManager().getHomeConfig().reload();
                sendMsg(sender, "§aConfiguração de homes recarregada com sucesso!");
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
