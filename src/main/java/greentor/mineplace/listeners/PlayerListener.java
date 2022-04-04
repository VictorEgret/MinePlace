package greentor.mineplace.listeners;

import greentor.mineplace.Mineplace;
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

    private final Mineplace plugin;

    private static HashMap<UUID, Integer> blocksPlaced = new HashMap<>();
    private static HashMap<UUID, Integer> tntPlaced = new HashMap<>();
    private static HashMap<UUID, Integer> bucketsPlaced = new HashMap<>();

    public PlayerListener(Mineplace plugin) {
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
                            }, 20 * 60 * 60 * 24L);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, 20 * 60 * 60 * 24L);
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
                        }, 20 * 60 * 60L);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, 20 * 60 * 60L);
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
                        }, 20 * 60 * 10L);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, 20 * 60 * 10L);
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
                        }, 20 * 60L);
                    }
                } else {
                    tntPlaced.put(e.getPlayer().getUniqueId(), 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        tntPlaced.replace(e.getPlayer().getUniqueId(), 0);
                    }, 20 * 60L);
                }
            }
        } else {
            if (blocksPlaced.containsKey(e.getPlayer().getUniqueId())) {
                if (e.getPlayer().hasPermission("default.use")) {

                    if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 5) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                    } else {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                        }, 20 * 60L);
                    }

                } else if (e.getPlayer().hasPermission("constructeur.use")) {

                    if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 10) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                    } else {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                        }, 20 * 60L);
                    }

                } else if (e.getPlayer().hasPermission("ingenieur.use")) {

                    if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 20) {
                        e.setCancelled(true);
                        e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                    } else {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                        plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                            blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                        }, 20 * 60L);
                    }
                }
            } else {
                if (e.getPlayer().isOp() || e.getPlayer().hasPermission("architecte.use")) return;
                blocksPlaced.put(e.getPlayer().getUniqueId(), 1);
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                }, 20 * 60L);
            }
        }
    }

    @EventHandler
    public void onWaterLava(PlayerBucketEmptyEvent e) {
        e.setCancelled(true);
        /*if (e.getPlayer().hasPermission("default.use") || e.getPlayer().hasPermission("constructeur.use") || e.getPlayer().hasPermission("ingenieur.use") || ) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(ChatColor.RED + "Tu dois avoir le grade architecte pour pouvoir utiliser ça");
            return;
        }
        if (bucketsPlaced.containsKey(e.getPlayer().getUniqueId())) {
            if (bucketsPlaced.get(e.getPlayer().getUniqueId()) == 1) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Tu a atteint la limite 'utilisation");
            } else {
                bucketsPlaced.replace(e.getPlayer().getUniqueId(), 1);
                plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                    bucketsPlaced.replace(e.getPlayer().getUniqueId(), 0);
                }, 20*60L);
            }
        } else {
            bucketsPlaced.put(e.getPlayer().getUniqueId(), 1);
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                bucketsPlaced.replace(e.getPlayer().getUniqueId(), 0);
            }, 20*60L);
        }*/
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (blocksPlaced.containsKey(e.getPlayer().getUniqueId())) {
            if (e.getPlayer().hasPermission("default.use")) {
                if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 5) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                    }, 20*60L);
                }
            } else if (e.getPlayer().hasPermission("constructeur.use")) {
                if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 10) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                    }, 20*60L);
                }
            } else if (e.getPlayer().hasPermission("ingenieur.use")) {
                if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 20) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                    }, 20*60L);
                }
            } else if (e.getPlayer().hasPermission("architecte.use")) {
                if (blocksPlaced.get(e.getPlayer().getUniqueId()) == 20) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte");
                } else {
                    blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) + 1);
                    plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                        blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
                    }, 20*60L);
                }
            }
        } else {
            if (e.getPlayer().isOp()) return;
            blocksPlaced.put(e.getPlayer().getUniqueId(), 1);
            plugin.getServer().getScheduler().runTaskLaterAsynchronously(plugin, () -> {
                blocksPlaced.replace(e.getPlayer().getUniqueId(), blocksPlaced.get(e.getPlayer().getUniqueId()) - 1);
            }, 20*60L);
        }
    }
}
