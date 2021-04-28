package de.sami.commands;

import de.sami.utils.Game;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JrListCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = (Player) sender;
        player.sendMessage("Folgende Spieler sind im Jump and Run: ");
        for (Game game : Game.running) {
            player.sendMessage(game.getPlayer().getName());
        }


        return true;
    }
}
