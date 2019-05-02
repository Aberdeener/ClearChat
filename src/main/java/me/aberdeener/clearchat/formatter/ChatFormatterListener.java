/*
 * MajorChat plugin project
 * This program is created by Aberdeener and yangyang200.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

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
