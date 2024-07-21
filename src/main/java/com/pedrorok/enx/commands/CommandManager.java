package com.pedrorok.enx.commands;

import com.pedrorok.enx.commands.sub.ReloadCmd;
import com.pedrorok.enx.commands.sub.HelpCmd;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.*;

public class CommandManager implements TabExecutor {

    public static final String PREFIX = "§7[§dEnxTest§7] §r";

    private static CommandManager instance;
    private final Map<String, Subcommand> commands = new HashMap<>();

    public CommandManager() {
        instance = this;
        commands.put("help", new HelpCmd());
        commands.put("reload", new ReloadCmd());
    }

    public static CommandManager get() {
        if (instance == null) {
            instance = new CommandManager();
        }
        return instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0) {
            sendFormMsg(sender,"Digite /"+ label +" help para ver os comandos.");
            return true;
        }

        Subcommand command = commands.getOrDefault(args[0], null);

        if (command == null) {
            sendFormMsg(sender, "§cComando desconhecido. Veja mais sobre digitando: §7/"+label+" help§c.");
            return true;
        }

        if (command.inGameOnly() && !(sender instanceof Player)) {
            sendFormMsg(sender,"Esse comando só pode ser executado em jogo.");

            return true;
        }

        if (!command.getPermission().equals("") && !sender.hasPermission(command.getPermission()) && !sender.hasPermission("enx.*")) {
            sendFormMsg(sender, "§cVocê não tem permissão para isso.");

            return true;
        }

        String[] subCmdArgs = new String[args.length - 1];
        System.arraycopy(args, 1, subCmdArgs, 0, subCmdArgs.length);

        if (!command.onCommand(sender, subCmdArgs)) {
            sendFormMsg(sender,"§cUso do comando: §7/"+label+" <gray>"  + command.getUsage() + "§c.");
        }

        return true;
    }

    public Collection<Subcommand> getCommands() {
        return commands.values();
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> toReturn = null;
        final String typed = args[0].toLowerCase();

        if (args.length == 1) {
            for (Map.Entry<String, Subcommand> entry : commands.entrySet()) {
                final String name = entry.getKey();
                final Subcommand subcommand = entry.getValue();

                if (name.startsWith(typed) && !subcommand.getPermission().equals("")
                        && (sender.hasPermission(subcommand.getPermission()) || sender.hasPermission("enx.*"))) {
                    if (toReturn == null) {
                        toReturn = new LinkedList<>();
                    }

                    toReturn.add(name);
                }
            }
        }

        if (args.length > 1) {
            final String subName = args[0];
            final Subcommand subcommand = commands.get(subName);

            if (subcommand != null) {
                toReturn = subcommand.onTabComplete(sender, args);
            }
        }

        return toReturn == null ? Collections.emptyList() : toReturn;
    }

    private void sendFormMsg(CommandSender sender, String message) {
        sender.sendMessage(CommandManager.PREFIX + message);
    }
}