package com.pedrorok.enx.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public abstract class SubCommand {

    protected CommandManager commandManager;

    public void setCommandManager(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

    protected abstract boolean onCommand(CommandSender sender, String[] args);

    protected abstract List<String> onTabComplete(CommandSender sender, String[] args);

    public abstract String getUsage();

    public abstract String getDescription();

    public boolean inGameOnly() {
        return false;
    }

    public String getPermission() {
        return "";
    }

    public void sendMsg(CommandSender sender, String message) {
        sender.sendMessage(commandManager.getPrefix() + message);
    }

    public boolean hasCmdPerm(CommandSender sender, String permission) {
        return sender.hasPermission(permission) || sender.hasPermission(commandManager.getPermissionPrefix() + "."+permission);
    }
}