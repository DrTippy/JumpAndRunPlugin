package de.sami.listeners;

import de.sami.utils.Game;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class MoveListener implements Listener {

    @EventHandler
    public synchronized void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();

        if (Game.isInGame(player)) {
            Location pLoc = player.getLocation();
            Game jnr = Game.getGame(player);

            assert jnr != null;

            // Player falls down (Tp back to jnr)
            if (pLoc.getBlockY() < jnr.currentHight) {
                jnr.getPlayer().teleport(jnr.getC().add(-1,1,-1));
                jnr.getC().subtract(-1,1,-1);
                player.playSound(pLoc, Sound.CAT_MEOW, 30, 30);
                jnr.fails++;
            }

            // Player is on the current block (Spawns the next block)
            if(checkLoc(pLoc, jnr.current)) {
                jnr.setNextBlock(jnr);
                pLoc.getWorld().getBlockAt(pLoc).setType(Game.currentBlockMat);
                player.playSound(pLoc, Sound.NOTE_BASS, 30, 30);
            }

        }
    }

    private boolean checkLoc(Location loc, Location current) {
        loc.subtract(0,1,0);
        boolean x = loc.getBlockX() == current.getBlockX();
        boolean y = loc.getBlockY() == current.getBlockY();
        boolean z = loc.getBlockZ() == current.getBlockZ();

        return x && y && z;
    }
}
