package greentor.mineplace;

import greentor.mineplace.listeners.PlayerListener;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Mineplace extends JavaPlugin {

    @Override
    public void onEnable() {

        registerListeners();

    }

    private void registerListeners() {
        Listener[] listeners = {new PlayerListener(this)};

        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
    }
}
