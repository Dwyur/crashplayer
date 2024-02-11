package me.dwyur.crashplayer;

import me.dwyur.crashplayer.command.CrashPlayerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class CrashPlayer extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        new CrashPlayerCommand(this);
    }
}
