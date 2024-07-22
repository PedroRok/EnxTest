package com.pedrorok.enx.commands.sub;

import com.pedrorok.enx.commands.SubCommand;
import com.pedrorok.enx.home.HomeManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class HomeRemoveCmd extends SubCommand {

    private final HomeManager homeManager;

    public HomeRemoveCmd(HomeManager homeManager) {
        this.homeManager = homeManager;
    }
    @Override
    protected boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            sendMsg(player, "§cUse /home remove <nome>");
            return true;
        }
        if (args.length == 1) {
            if (homeManager.removePlayerHome(player.getUniqueId(), args[0].toLowerCase())) {
                sendMsg(player, "§aHome removida com sucesso.");
                return true;
            }
            sendMsg(player, "§cHome não encontrada.");
            return true;
        }
        return false;
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args) {
        if (args.length != 1) return null;
        Set<String> strings = homeManager.getPlayerHomes(((Player) sender).getUniqueId()).getHomes().keySet();
        return new ArrayList<>(strings);
    }

    @Override
    public String getUsage() {
        return "home remove <nome>";
    }

    @Override
    public String getDescription() {
        return "Remove uma home.";
    }
}
