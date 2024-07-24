package com.pedrorok.enx.commands.sub;

import com.pedrorok.enx.commands.SubCommand;
import com.pedrorok.enx.home.HomeManager;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author Rok, Pedro Lucas nmm. Created on 21/07/2024
 * @project EnxTest
 */
public class HomeSetCmd extends SubCommand {

    private final HomeManager homeManager;

    public HomeSetCmd(HomeManager homeManager) {
        this.homeManager = homeManager;
    }

    @Override
    protected boolean onCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;
        if (args.length == 0) {
            Location home = homeManager.getPlayerHomes(player.getUniqueId()).getHome("home");
            if (home == null) {
                homeManager.savePlayerHome(player, "home", player.getLocation());
                sendMsg(player, "§aHome setada com sucesso.");
                return true;
            }
            sendMsg(sender, "§cVocê já possui uma home padrão. §7(§f/home set home §7 para alterar)");
            return true;
        }
        homeManager.savePlayerHome(player, args[0].toLowerCase(), player.getLocation());
        sendMsg(player, "§aHome setada com sucesso.");
        return true;
    }

    @Override
    public boolean inGameOnly() {
        return super.inGameOnly();
    }

    @Override
    protected List<String> onTabComplete(CommandSender sender, String[] args) {
        return List.of();
    }

    @Override
    public String getUsage() {
        return "home set [nome]";
    }

    @Override
    public String getDescription() {
        return "Seta uma home.";
    }
}
