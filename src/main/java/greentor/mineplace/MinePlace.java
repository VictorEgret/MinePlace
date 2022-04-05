package greentor.mineplace;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.Objects;

public final class MinePlace extends JavaPlugin {

    @Override
    public void onEnable() {
        registerListeners();
        registerCommands();
    }

    private void registerCommands() {
        String[] commands = {"site", "discord", "spawn"};

        Arrays.stream(commands).forEach(command -> Objects.requireNonNull(this.getCommand(command)).setExecutor(new Commands()));
    }

    private void registerListeners() {
        Listener[] listeners = {new PlayerListener()};

        for (Listener listener : listeners) {
            this.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {}
}
