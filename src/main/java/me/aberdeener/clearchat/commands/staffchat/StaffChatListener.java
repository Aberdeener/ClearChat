package me.aberdeener.clearchat.commands.staffchat;

import me.aberdeener.clearchat.ClearChat;
import me.aberdeener.clearchat.utils.VaultUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListener implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        if (VaultUtils.hasPermission(e.getPlayer(), "majorchat.staff")) {
            if (e.getPlayer().hasMetadata("inStaffChat")) {
                StaffChatCommand.postStaffChat(VaultUtils.getFullName(e.getPlayer()), e.getMessage());
                e.setCancelled(true);
            }

            FileConfiguration data = ClearChat.getInstance().getData();
            if (data.contains("staff-chat." + e.getPlayer().getUniqueId().toString() + ".prefix")) {
                if (e.getMessage().startsWith(data.getString("staff-chat." + e.getPlayer().getUniqueId().toString() + ".prefix"))) {
                    StaffChatCommand.postStaffChat(VaultUtils.getFullName(e.getPlayer()), e.getMessage().replaceFirst(data.getString("staff-chat." + e.getPlayer().getUniqueId().toString() + ".prefix"),
                            ""));
                    e.setCancelled(true);
                }
            }
        }
    }
}
