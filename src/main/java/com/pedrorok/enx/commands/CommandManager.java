package com.pedrorok.enx.commands;

import com.pedrorok.enx.commands.sub.HelpCmd;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandManager implements TabExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();

    @Getter
    private final String prefix;
    @Getter
    private final String permissionPrefix;

    public CommandManager(String permissionPrefix, String prefix) {
        this.prefix = prefix;
        this.permissionPrefix = permissionPrefix;
        registerSubCommand("help", new HelpCmd());
    }

    public void registerSubCommand(String name, SubCommand subcommand) {
        commands.put(name, subcommand);
        subcommand.setCommandManager(this);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendMsg(sender,"Digite /"+ label +" help para ver os comandos.");
            return true;
        }

        SubCommand command = commands.get(args[0]);

        if (command == null) {
            return false;
        }

        if (command.inGameOnly() && !(sender instanceof Player)) {
            sendMsg(sender,"Esse comando só pode ser executado em jogo.");

            return true;
        }

        if (!command.getPermission().isEmpty() && !sender.hasPermission(command.getPermission()) && !sender.hasPermission(permissionPrefix+".*")) {
            sendMsg(sender, "§cVocê não tem permissão para isso.");
            return true;
        }

        String[] subCmdArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subCmdArgs, 0, subCmdArgs.length);

        if (!command.onCommand(sender, subCmdArgs)) {
            sendMsg(sender,"§cUso do comando: §7/"+label+" <gray>"  + command.getUsage() + "§c.");
        }

        return true;
    }

    public Collection<SubCommand> getCommands() {
        return commands.values();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> toReturn = null;
        final String typed = args[0].toLowerCase();

        if (args.length == 1) {
            for (Map.Entry<String, SubCommand> entry : commands.entrySet()) {
                final String name = entry.getKey();
                final SubCommand subcommand = entry.getValue();

                if ((name.startsWith(typed) && ((sender.hasPermission(subcommand.getPermission()) || sender.hasPermission(permissionPrefix+".*")) || subcommand.getPermission().isEmpty()))) {
                    if (toReturn == null) {
                        toReturn = new LinkedList<>();
                    }

                    toReturn.add(name);
                }
            }
        }

        if (args.length > 1) {
            final String subName = args[0];
            final SubCommand subcommand = commands.get(subName);

            if (subcommand != null) {
                toReturn = subcommand.onTabComplete(sender, args);
            }
        }

        return toReturn == null ? Collections.emptyList() : toReturn;
    }

    protected void sendMsg(CommandSender sender, String message) {
        sender.sendMessage(prefix + message);
    }
}