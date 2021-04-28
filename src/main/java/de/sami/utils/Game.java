package de.sami.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    public Location n, c, o;

    public static final Material currentBlockMat = Material.STAINED_GLASS;
    public static final Material nextBlockMat = Material.WOOL;

    public static List<Game> running = new ArrayList<>();

    public Player player;
    public int fails, score, currentHight, min, sec;
    public Location current;
    public boolean starting;

    public Game (Player player, Location current) {
        this.player = player;
        this.fails = 0;
        this.score = 0;
        this.min = 0;
        this.sec = 0;
        this.current = current;
        this.currentHight = player.getLocation().getBlockY();
        this.starting = true;
    }

    public static Boolean isInGame(Player player) {
        for (Game game : running) {
            if (game.getPlayer().getName().equals(player.getName())) return true;
        }
        return false;
    }

    public static Game getGame(Player player) {
        for (Game game : running) {
            if (game.getPlayer().getName().equals(player.getName())) return game;
        }
        return null;
    }

    public static void addGame(Player player, Location loc) {
        Game game = new Game(player, loc);
        running.add(game);
    }

    public static void placeStartBlock(Location start) {
        start.subtract(0,2,0);
        start.getWorld().getBlockAt(start).setType(currentBlockMat);
    }

    public void setNextBlock(Game game) {

        Location nextLoc = getEmptyloc();
        assert nextLoc != null;

        o = c;
        c = player.getLocation().subtract(-1,1,-1);
        n = nextLoc;


        if (o != null) {
            if (score == 1) o.subtract(0,1,0);

            o.subtract(1,0,1);
            o.getWorld().getBlockAt(o).setType(Material.AIR);
        }

        nextLoc.getWorld().getBlockAt(nextLoc).setType(nextBlockMat);

        setCurrent(nextLoc);
        setCurrentHight(nextLoc.getBlockY());
        score++;
    }

    private Location getEmptyloc() {
        for (int i = 0; i <= 1000; i++) {
            Location a = getRandomLoc();
            if (a.getWorld().getBlockAt(a).getType() == Material.AIR) return a;
        }
        return null;
    }

    private Location getRandomLoc() {
        Random r = new Random();
        int x = r.nextInt(3) + 1;
        int y = r.nextInt(2);
        int z = r.nextInt(3) + 1;

        if (x == 4  && z == 4) {
            x = r.nextInt(3) + 1;
            z = r.nextInt(3) + 1;
        }

        if (r.nextBoolean()) x *= -1;
        if (r.nextBoolean()) z *= -1;

        Location d = current;
        return d.add(x,y,z);
    }

    public Player getPlayer() { return player; }

    public void setCurrent(Location current) { this.current = current; }

    public void setCurrentHight(int currentHight) { this.currentHight = currentHight; }

    public Location getCurrent() { return current; }

    public  Location getC() { return c; }

    public int getMin() { return min; }

    public int getSec() { return sec; }

    public void setMin(int min) { this.min = min; }

    public void setSec(int sec) { this.sec = sec; }
}
