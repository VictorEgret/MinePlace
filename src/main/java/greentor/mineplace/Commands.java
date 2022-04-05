package greentor.mineplace;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (cmd.getName().equalsIgnoreCase("site")) {
            sender.sendMessage(ChatColor.GOLD + "Site bientôt disponible");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("discord")) {
            sender.sendMessage(ChatColor.GOLD + "https://discord.gg/jYMU5g84jR");
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("spawn")) {
            if (sender instanceof Player) {
                Player p = ((Player) sender);
                Location spawn = p.getWorld().getSpawnLocation();
                spawn.setYaw(-90F);
                p.teleport(spawn);            }
            return true;
        }

        return false;
    }
}
