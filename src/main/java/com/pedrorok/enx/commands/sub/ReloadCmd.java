package com.pedrorok.enx.commands.sub;

import com.pedrorok.enx.Main;
import com.pedrorok.enx.commands.Subcommand;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 20/07/2024
 * @project EnxTest
 */
public class ReloadCmd implements Subcommand {
    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        Main.get().getWindManager().getWindConfig().reload();
        sendMsg(sender, "§aConfiguração recarregada com sucesso!");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getUsage() {
        return "reload";
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
