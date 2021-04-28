package de.sami.utils;

import de.sami.main.Main;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Actionbar {

    static FileConfiguration config = Main.instance.getConfig();

    private static void sendActionbar(final Player player, final String message) {
        final IChatBaseComponent iChatBaseComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('§', message) + "\"}");
        final PacketPlayOutChat packet = new PacketPlayOutChat(iChatBaseComponent, (byte) 2);

        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public static void updateScore() {
        for (Game game : Game.running) {
            Player player = game.getPlayer();

            if (game.score == 100) {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "Du hast das Jump and Run geschafft.");
                Game.running.remove(game);
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
            game.sec++;
            if (game.sec % 60 == 0 && game.sec != 0) {
                game.min++;
                game.sec = 0;
            }
            String timer = ", Timer: " + game.min + ":" + game.sec;

            final String message = "§e" + "Score: " + game.score + ", Fails: " + game.fails + timer;
            sendActionbar(player, message);
        }

    }

}
