package greentor.mineplace;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.scheduler.BukkitTask;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerListener implements Listener {

    private final MinePlace plugin;

    private static final long ONE_MINUTE = 20 * 60;
    private static final long ONE_MINUTE_MILLIS = 1000 * 60;

    private static final HashMap<UUID, Integer> interactions = new HashMap<>();
    private static final HashMap<UUID, ArrayList<Long>> interactions2 = new HashMap<>();
    private static final HashMap<UUID, Integer> tntPlaced = new HashMap<>();
    private static final HashMap<UUID, ArrayList<Long>> tntPlaced2 = new HashMap<>();

    public PlayerListener(MinePlace plugin) {
        this.plugin = plugin;
    }

    private void deleteExpired(ArrayList<Long> list, long delay) {
        list.removeIf(l -> System.currentTimeMillis() - l >= delay);
    }

    private String getNextInteraction(ArrayList<Long> list, long delay) {
        if (list == null || list.isEmpty()) return "";
        long minimum = Collections.min(list);
        DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(delay - (System.currentTimeMillis() - minimum));
    }

    private void handleBlockPlace2(BlockPlaceEvent e, long waitTime, int placeLimit) {
        if (e.getBlock().getType() == Material.TNT) {
            if (tntPlaced2.containsKey(e.getPlayer().getUniqueId())) {
                deleteExpired(tntPlaced2.get(e.getPlayer().getUniqueId()), waitTime);
                if (tntPlaced2.get(e.getPlayer().getUniqueId()).size() == 1) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte, tnt disponible dans ");
                } else {
                    tntPlaced.replace(e.getPlayer().getUniqueId(), 1);
                }
            } else {
                if (e.getPlayer().isOp()) return;
                tntPlaced2.put(e.getPlayer().getUniqueId(), new ArrayList<>(Collections.singletonList(System.currentTimeMillis())));
            }
        } else {
            if (placeLimit == 0) return;
            if (interactions2.containsKey(e.getPlayer().getUniqueId())) {
                deleteExpired(interactions2.get(e.getPlayer().getUniqueId()), ONE_MINUTE_MILLIS);
                if (interactions2.get(e.getPlayer().getUniqueId()).size() == placeLimit) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte, interaction disponible dans " + getNextInteraction(interactions2.get(e.getPlayer().getUniqueId()), ONE_MINUTE_MILLIS));
                } else {
                    interactions2.get(e.getPlayer().getUniqueId()).add(System.currentTimeMillis());
                }
            } else {
                if (e.getPlayer().isOp()) return;
                interactions2.put(e.getPlayer().getUniqueId(), new ArrayList<>(Collections.singletonList(System.currentTimeMillis())));
            }
        }
    }

    private void handleBlockPlace(BlockPlaceEvent e, long waitTime, int placeLimit) {
        if (e.getBlock().getType() == Material.TNT) {
            if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                if (tntPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte, tnt disponible dans ");
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
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte, interaction disponible dans ");
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

            //handleBlockPlace(e, ONE_MINUTE * 60 * 24, 5);
            handleBlockPlace2(e, ONE_MINUTE_MILLIS * 60 * 24, 5);

        } else if (e.getPlayer().hasPermission("constructeur.use")) {

            //handleBlockPlace(e, ONE_MINUTE * 60, 10);
            handleBlockPlace2(e, ONE_MINUTE_MILLIS * 60, 10);


        } else if (e.getPlayer().hasPermission("ingenieur.use")) {

            //handleBlockPlace(e, ONE_MINUTE * 10, 20);
            handleBlockPlace2(e, ONE_MINUTE_MILLIS * 10, 20);

        } else if (e.getPlayer().hasPermission("architecte.use")) {

            //handleBlockPlace(e, ONE_MINUTE, 0);
            handleBlockPlace2(e, ONE_MINUTE_MILLIS, 0);

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
                e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte, interaction disponible dans ");
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
