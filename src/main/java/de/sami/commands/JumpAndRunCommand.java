package de.sami.commands;

import de.sami.main.Main;
import de.sami.utils.Game;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;


public class JumpAndRunCommand implements CommandExecutor {

    public FileConfiguration config = Main.instance.getConfig();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Player player = (Player) sender;

        if (args.length == 0) {
            sendUsage(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start": {
                start(player);
                break;
            }
            case "leave": {
                leave(player);
                break;
            }
            default: {
                sendUsage(sender);
                break;
            }
        }
        return true;
    }

    public void start(Player player) {
        if (!Game.isInGame(player)) {
            if (checkBlockAbove(player)) {
                player.sendMessage(Main.getPrefix() + ChatColor.GREEN + "Du hast das Jump and Run gestartet");
                Location start = player.getLocation().add(0,25,0);
                Game.addGame(player, start);

                player.teleport(player.getLocation().add(0,25,0));

                Game game = Game.getGame(player);
                assert game != null;
                Game.placeStartBlock(start);
                game.setNextBlock(game);
            } else {
                player.sendMessage(Main.getPrefix() + ChatColor.RED +  "Du hast Blöcke über dir, gehe woanders hin!");
            }
        } else {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Du bist bereits in einem Jump and Run!");
        }



    }

    public void leave(Player player) {
        if (!Game.isInGame(player)) {
            player.sendMessage(Main.getPrefix() + ChatColor.RED + "Du musst zuerst das Jump and Run starten.");
        } else {
            player.sendMessage(Main.getPrefix() + ChatColor.GREEN + "Du hast das Jump and Run beendet.");
            Game game = Game.getGame(player);
            Game.running.remove(game);

            assert game != null;
            game.current.getWorld().getBlockAt(game.current).setType(Material.AIR);
            Location c = player.getLocation().subtract(0,1,0);
            c.getWorld().getBlockAt(c).setType(Material.AIR);

            String nH = player.getName() + ".highscore";
            String nF = player.getName() + ".fails";
            String nTM = player.getName() + ".timer.min";
            String nTS = player.getName() + ".timer.sec";

            config.set(nH, game.score);
            config.set(nF, game.fails);
            config.set(nTM, game.min);
            config.set(nTS, game.sec);
            Main.instance.saveConfig();

        }
    }

    private boolean checkBlockAbove(Player player) {
        Location loc = player.getEyeLocation().add(0,1,0);
        while (loc.getY() < 256) {
            if (loc.getBlock().getType() != Material.AIR) {
                return false;
            }
            loc.add(0,1,0);
        }
        return true;
    }

    private void sendUsage(CommandSender sender) {
        sender.sendMessage(Main.getPrefix() + ChatColor.RED + " /jr start, /timer leave");
    }


}
