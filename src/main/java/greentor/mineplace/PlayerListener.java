package greentor.mineplace;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class PlayerListener implements Listener {

    private static final long ONE_MINUTE_MILLIS = 1000 * 60;

    private static final HashMap<UUID, ArrayList<Long>> interactions = new HashMap<>();
    private static final HashMap<UUID, ArrayList<Long>> tntPlaced = new HashMap<>();

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

    private void handleBlockPlace(BlockPlaceEvent e, long waitTime, int placeLimit) {
        if (e.getBlock().getType() == Material.TNT) {
            if (tntPlaced.containsKey(e.getPlayer().getUniqueId())) {
                ArrayList<Long> playerTnt = tntPlaced.get(e.getPlayer().getUniqueId());
                deleteExpired(playerTnt, waitTime);
                if (playerTnt.size() == 1) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite de tnt atteinte, tnt disponible dans " + getNextInteraction(playerTnt, waitTime));
                } else {
                    if (e.isCancelled()) return;
                    playerTnt.add(System.currentTimeMillis());
                }
            } else {
                if (e.getPlayer().isOp() || e.isCancelled()) return;
                tntPlaced.put(e.getPlayer().getUniqueId(), new ArrayList<>(Collections.singletonList(System.currentTimeMillis())));
            }
        } else {
            if (placeLimit == 0) return;
            if (interactions.containsKey(e.getPlayer().getUniqueId())) {
                ArrayList<Long> playerInteractions = interactions.get(e.getPlayer().getUniqueId());
                deleteExpired(playerInteractions, ONE_MINUTE_MILLIS);
                if (playerInteractions.size() == placeLimit) {
                    e.setCancelled(true);
                    e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte, interaction disponible dans " + getNextInteraction(playerInteractions, ONE_MINUTE_MILLIS));
                } else {
                    if (e.isCancelled()) return;
                    playerInteractions.add(System.currentTimeMillis());
                }
            } else {
                if (e.getPlayer().isOp() || e.isCancelled()) return;
                interactions.put(e.getPlayer().getUniqueId(), new ArrayList<>(Collections.singletonList(System.currentTimeMillis())));
            }
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {

        if (e.getPlayer().hasPermission("default.use")) {

            handleBlockPlace(e, ONE_MINUTE_MILLIS * 60 * 24, 5);

        } else if (e.getPlayer().hasPermission("constructeur.use")) {

            handleBlockPlace(e, ONE_MINUTE_MILLIS * 60, 10);

        } else if (e.getPlayer().hasPermission("ingenieur.use")) {

            handleBlockPlace(e, ONE_MINUTE_MILLIS * 10, 15);

        } else if (e.getPlayer().hasPermission("architecte.use")) {

            handleBlockPlace(e, ONE_MINUTE_MILLIS, 20);

        }
    }

    @EventHandler
    public void onWaterLava(PlayerBucketEmptyEvent e) {
        if (e.getPlayer().isOp()) return;
        e.setCancelled(true);
    }

    public void handleBlockBreak(BlockBreakEvent e, long waitTime, int breakLimit) {
        if (interactions.containsKey(e.getPlayer().getUniqueId())) {
            ArrayList<Long> playerInteractions = interactions.get(e.getPlayer().getUniqueId());
            deleteExpired(interactions.get(e.getPlayer().getUniqueId()), waitTime);
            if (playerInteractions.size() == breakLimit) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "Limite d'interactions atteinte, interaction disponible dans " + getNextInteraction(playerInteractions, waitTime));
            } else {
                if (e.isCancelled()) return;
                playerInteractions.add(System.currentTimeMillis());
            }
        } else {
            if (e.getPlayer().isOp() || e.isCancelled()) return;
            interactions.put(e.getPlayer().getUniqueId(), new ArrayList<>(Collections.singletonList(System.currentTimeMillis())));
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        if (e.getPlayer().hasPermission("default.use")) {

            handleBlockBreak(e, ONE_MINUTE_MILLIS , 5);

        } else if (e.getPlayer().hasPermission("constructeur.use")) {

            handleBlockBreak(e, ONE_MINUTE_MILLIS, 10);

        } else if (e.getPlayer().hasPermission("ingenieur.use")) {

            handleBlockBreak(e, ONE_MINUTE_MILLIS, 15);

        } else if (e.getPlayer().hasPermission("architecte.use")) {

            handleBlockBreak(e, ONE_MINUTE_MILLIS, 20);

        }
    }
}
