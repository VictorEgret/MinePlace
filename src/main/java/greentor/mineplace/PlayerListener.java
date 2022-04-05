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

    private static HashMap<UUID, Integer> interactions = new HashMap<>();
    private static HashMap<UUID, Integer> tntPlaced = new HashMap<>();

    public PlayerListener(MinePlace plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (e.getBlock().getType() == Material.TNT) {

            if (e.getPlayer().hasPermission("default.use")) {

                if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                    if (tntPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte");
                    } else {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                            }, ONE_MINUTE * 60 * 24);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, ONE_MINUTE * 60 * 24);
                }

            } else if (e.getPlayer().hasPermission("constructeur.use")) {

                if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                    if (tntPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte");
                    } else {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                        }, ONE_MINUTE * 60);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, ONE_MINUTE * 60);
                }

            } else if (e.getPlayer().hasPermission("ingenieur.use")) {

                if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                    if (tntPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte");
                    } else {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                        }, ONE_MINUTE * 10);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, ONE_MINUTE * 10);
                }

            } else if (e.getPlayer().hasPermission("architecte.use")) {

                if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                    if (tntPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte");
                    } else {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                        }, ONE_MINUTE);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, ONE_MINUTE);
                }
            }
        } else { // Si le joueur ne pose pas de TNT
            if (interactions.containsKey(e.getPlayer().getUniqueId())) {

                if (e.getPlayer().hasPermission("default.use")) {

                    if (interactions.get(e.getPlayer().getUniqueId()) == 5) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                    } else {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                        }, ONE_MINUTE);
                    }

                } else if (e.getPlayer().hasPermission("constructeur.use")) {

                    if (interactions.get(e.getPlayer().getUniqueId()) == 10) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                    } else {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                        }, ONE_MINUTE);
                    }

                } else if (e.getPlayer().hasPermission("ingenieur.use")) {

                    if (interactions.get(e.getPlayer().getUniqueId()) == 20) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                    } else {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                        }, ONE_MINUTE);
                    }
                }
            } else { // Première interaction
                if (e.getPlayer().isOp() || e.getPlayer().hasPermission("architecte.use")) return;
                interactions.put(e.getPlayer().getUniqueId(), 1);
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                }, ONE_MINUTE);
            }
        }
    }

    @EventHandler
    public void onWaterLava(PlayerBucketEmptyEvent e) {
        if (e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (interactions.containsKey(e.getPlayer().getUniqueId())) {

            if (e.getPlayer().hasPermission("default.use")) {
                if (interactions.get(e.getPlayer().getUniqueId()) == 5) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                    }, ONE_MINUTE);
                }

            } else if (e.getPlayer().hasPermission("constructeur.use")) {
                if (interactions.get(e.getPlayer().getUniqueId()) == 10) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                    }, ONE_MINUTE);
                }

            } else if (e.getPlayer().hasPermission("ingenieur.use")) {
                if (interactions.get(e.getPlayer().getUniqueId()) == 20) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                    }, ONE_MINUTE);
                }

            } else if (e.getPlayer().hasPermission("architecte.use")) {
                if (interactions.get(e.getPlayer().getUniqueId()) == 20) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
                    }, ONE_MINUTE);
                }
            }
        } else { // Première interaction
            if (e.getPlayer().isOp()) return;
            interactions.put(e.getPlayer().getUniqueId(), 1);
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                interactions.replace(e.getPlayer().getUniqueId(), interactions.get(e.getPlayer().getUniqueId()) - 1);
            }, ONE_MINUTE);
        }
    }
}
