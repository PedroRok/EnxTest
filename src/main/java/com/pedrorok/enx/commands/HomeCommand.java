package com.pedrorok.enx.commands;

import com.pedrorok.enx.commands.sub.HelpCmd;
import com.pedrorok.enx.commands.sub.HomeRemoveCmd;
import com.pedrorok.enx.commands.sub.HomeSetCmd;
import com.pedrorok.enx.home.HomeManager;
import com.pedrorok.enx.home.PlayerHomes;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class HomeCommand extends CommandManager {

    private final HomeManager homeManager;
    public HomeCommand(HomeManager homeManager) {
        super("home", "§7[§fHomes§7] §r");
        this.homeManager = homeManager;

        registerSubCommand("set", new HomeSetCmd(homeManager));
        registerSubCommand("remove", new HomeRemoveCmd(homeManager));
        registerSubCommand("help", new HelpCmd("home"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player p)) {
            sendMsg(sender, "§cApenas jogadores podem executar esse comando.");
            return true;
        }
        if (args.length == 0) {
            PlayerHomes playerHomes = homeManager.getPlayerHomes(p.getUniqueId());
            if (playerHomes.getHomes().isEmpty()) {
                sendMsg(p, "§cVocê não possui nenhuma home.");
                return true;
            }
            if (playerHomes.getHomes().size() == 1 || playerHomes.getHome("home") == null) {
                playerHomes.teleportToHome(p, playerHomes.getHomes().entrySet().stream().findFirst().get().getValue());
                return true;
            }
            playerHomes.teleportToHome(p, "home");
            return true;
        }
        if (super.onCommand(sender,cmd,label,args)) return true;
        if (!homeManager.getPlayerHomes(p.getUniqueId()).teleportToHome(p, args[0])) {
            sendMsg(p, "§cEssa home não existe.");
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        List<String> strings = super.onTabComplete(sender, cmd, label, args);
        if (strings == null || strings.isEmpty()) {
            strings = new ArrayList<>();
        }
        if (args.length == 1 || (args.length == 2 && args[0].equalsIgnoreCase("remove"))) {
            PlayerHomes playerHomes = homeManager.getPlayerHomes(((Player) sender).getUniqueId());
            if (playerHomes.getHomes().isEmpty()) return strings;
            strings.addAll(playerHomes.getHomes().keySet());
        }
        return strings;
    }
}
