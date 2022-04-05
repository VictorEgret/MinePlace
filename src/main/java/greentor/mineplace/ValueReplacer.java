package greentor.mineplace;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ValueReplacer extends BukkitRunnable {

    private final UUID playerId;
    private final int newValue;
    private final HashMap<UUID, Integer> map;
    private int time;

    public ValueReplacer(UUID playerId, int newValue, HashMap<UUID, Integer> map) {
        this.playerId = playerId;
        this.newValue = newValue;
        this.map = map;
    }

    @Override
    public void run() {
        if (time == 0) {
            map.replace(this.playerId, this.newValue);
        } else {
            time--;
        }
    }
}
