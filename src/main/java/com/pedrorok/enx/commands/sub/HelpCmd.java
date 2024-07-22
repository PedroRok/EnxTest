package com.pedrorok.enx.commands.sub;

import com.pedrorok.enx.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class HelpCmd extends SubCommand {
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

        for (SubCommand cmd : commandManager.getCommands()) {
            if (cmd.inGameOnly() && !(sender instanceof Player) || (!cmd.getPermission().isEmpty() && !sender.hasPermission(cmd.getPermission()) && !hasCmdPerm(sender, "*"))) {
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