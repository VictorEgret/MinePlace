package greentor.mineplace;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.util.HashMap;
import java.util.UUID;

public class PlayerListener implements Listener {

    private final MinePlace plugin;

    private static final long ONE_MINUTE = 20 * 60;

    private static final HashMap<UUID, Integer> interactions = new HashMap<>();
    private static final HashMap<UUID, Integer> tntPlaced = new HashMap<>();

    public PlayerListener(MinePlace plugin) {
        this.plugin = plugin;
    }

    private void handleBlockPlace(BlockPlaceEvent e, long waitTime, int placeLimit) {
        if (e.getBlock().getType() == Material.TNT) {
            if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                if (tntPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte");
                } else {
                    tntPlaced.replace(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, waitTime);
                }
            } else {
                if (e.getPlayer().isOp()) return;
                tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                }, waitTime);
            }
        } else {
            if (placeLimit == 0) return;
            if (interactions.containsKey(e.getPlayer().getUniqueId())) {
                if (interactions.get(e.getPlayer().getUniqueId()) == placeLimit) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                    }, waitTime);
                }
            } else {
                if (e.getPlayer().isOp()) return;
                interactions.put(e.getPlayer().getUniqueId(), 1);
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                }, waitTime);
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (e.getPlayer().hasPermission("default.use")) {

            handleBlockPlace(e, ONE_MINUTE * 60 * 24, 5);

        } else if (e.getPlayer().hasPermission("constructeur.use")) {

            handleBlockPlace(e, ONE_MINUTE * 60, 10);

        } else if (e.getPlayer().hasPermission("ingenieur.use")) {

            handleBlockPlace(e, ONE_MINUTE * 10, 20);

        } else if (e.getPlayer().hasPermission("architecte.use")) {

            handleBlockPlace(e, ONE_MINUTE, 0);

        }
    }

    @EventHandler
    public void onWaterLava(PlayerBucketEmptyEvent e) {
        if (e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    public void handleBlockBreak(BlockBreakEvent e, long waitTime, int breakLimit) {
        if (interactions.containsKey(e.getPlayer().getUniqueId())) {
            if (interactions.get(e.getPlayer().getUniqueId()) == breakLimit) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
            } else {
                interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                }, waitTime);
            }
        } else {
            if (e.getPlayer().isOp()) return;
            interactions.put(e.getPlayer().getUniqueId(), 1);
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
            }, waitTime);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().hasPermission("default.use")) {

            handleBlockBreak(e, ONE_MINUTE * 60 * 24, 5);

        } else if (e.getPlayer().hasPermission("constructeur.use")) {

            handleBlockBreak(e, ONE_MINUTE * 60, 10);

        } else if (e.getPlayer().hasPermission("ingenieur.use")) {

            handleBlockBreak(e, ONE_MINUTE * 10, 20);

        } else if (e.getPlayer().hasPermission("architecte.use")) {

            handleBlockBreak(e, ONE_MINUTE, 20);

        }
    }
}
