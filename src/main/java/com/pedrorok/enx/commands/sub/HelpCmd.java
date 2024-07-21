package com.pedrorok.enx.commands.sub;

import com.pedrorok.enx.commands.CommandManager;
import com.pedrorok.enx.commands.Subcommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCmd implements Subcommand {

    @Override
    public String getUsage() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Mostra essa página.";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        sendMsg(sender,"§eLista de comandos:");

        for (Subcommand cmd : CommandManager.get().getCommands()) {
            if (cmd.inGameOnly() && !(sender instanceof Player) || (!cmd.getPermission().isEmpty() && !sender.hasPermission(cmd.getPermission()) && !sender.hasPermission("swm.*"))) {
                continue;
            }

            sendMsg(sender," - §e/enx " + cmd.getUsage() + "§7 - " + cmd.getDescription());
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }
}