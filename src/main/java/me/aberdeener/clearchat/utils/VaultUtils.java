/*
 * The VaultCore project
 * This program is created by yangyang200, and some of the VaultMC developers.
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

package me.aberdeener.clearchat.utils;

import me.aberdeener.clearchat.ClearChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Offers a small interface to VaultAPI.
 *
 * @author yangyang200
 */
public final class VaultUtils {
    /**
     * Prevent accidental construction
     */
    private VaultUtils() { }

    /**
     * Player has a permission
     *
     * @param sender The sender to test
     * @param node   The permissions node
     * @return If the sender has the permission
     */
    public static boolean hasPermission(CommandSender sender, String node) {
        return ClearChat.permission.has(sender, node);
    }

    /**
     * Get the prefix of a player.
     * @param player The player to get
     * @return The prefix
     */
    public static String getPrefix(Player player) {
        return ChatColor.translateAlternateColorCodes('&', ClearChat.chat.getPlayerPrefix(player));
    }

    /**
     * Get the suffix of a player
     * @param player The player to get
     * @return The suffix
     */
    public static String getSuffix(Player player) {
        return ChatColor.translateAlternateColorCodes('&', ClearChat.chat.getPlayerSuffix(player));
    }

    /**
     * Get the full name of a player
     * @param player The player to get
     * @return The full name
     */
    public static String getFullName(Player player) {
        return ChatColor.translateAlternateColorCodes('&', getPrefix(player) + player.getDisplayName() + getSuffix(player));
    }
}
