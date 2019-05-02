package me.aberdeener.clearchat.formatter;

import me.aberdeener.clearchat.ClearChat;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatFormatterListener implements Listener {
    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (ClearChat.getInstance().getConfig().getBoolean("formatter.enabled")) {
            e.setFormat(ChatColor.translateAlternateColorCodes('&', ClearChat.getInstance().getConfig().getString("formatter.format").replace("%player%", "%1$s")
                .replace("%message%", "%1$s")));
        }
    }
}
