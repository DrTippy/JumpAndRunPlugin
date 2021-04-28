package de.sami.main;

import de.sami.commands.JrListCommand;
import de.sami.commands.JumpAndRunCommand;
import de.sami.listeners.DamageListener;
import de.sami.listeners.MoveListener;
import de.sami.utils.Actionbar;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class Main extends JavaPlugin {

   public static Main instance;
   public FileConfiguration config = getConfig();

   @Override
   public void onLoad() {
      instance = this;
   }

    @Override
    public void onEnable() {

        commandRegistration();
        listenerRegistration();

        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, Actionbar::updateScore, 0L,20L);

    }

    private void listenerRegistration() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new MoveListener(), this);
        pluginManager.registerEvents(new DamageListener(), this);
    }

    private void commandRegistration() {
        getCommand("jr").setExecutor(new JumpAndRunCommand());
        getCommand("jrlist").setExecutor(new JrListCommand());
    }


    public static Main getInstance() {return instance;}


    public static String getPrefix() { return ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "Jump and Run" + ChatColor.DARK_GRAY + "] "; }
}
