package me.aberdeener.clearchat.joinleave;

import me.aberdeener.clearchat.ClearChat;
import me.aberdeener.clearchat.utils.VaultUtils;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinLeaveMessages implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        FileConfiguration config = ClearChat.getInstance().getConfig();
        if (config.getBoolean("join-quit-message.enabled")) {
            e.setJoinMessage(
                    ChatColor.translateAlternateColorCodes('&', config.getString("join-quit-message.join-message")
                            .replace("%displayname%", e.getPlayer().getDisplayName())
                            .replace("%username%", e.getPlayer().getName())
                            .replace("%player%", VaultUtils.getFullName(e.getPlayer())))
            );
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        FileConfiguration config = ClearChat.getInstance().getConfig();
        if (config.getBoolean("join-quit-message.enabled")) {
            e.setQuitMessage(
                    ChatColor.translateAlternateColorCodes('&', config.getString("join-quit-message.quit-message")
                        .replace("%displayname%", e.getPlayer().getDisplayName())
                        .replace("%username%", e.getPlayer().getName())
                        .replace("%player%", VaultUtils.getFullName(e.getPlayer())))
            );
        }
    }
}
