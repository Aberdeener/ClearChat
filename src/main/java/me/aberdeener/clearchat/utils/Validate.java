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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Basic information verification
 * Static
 *
 * @author yangyang200
 */
public final class Validate {
    /**
     * Prevent accidental construction
     */
    private Validate() { }

    /**
     * Returns if a player online with the specified name
     * @param p The player name
     * @return If the player is online
     */
    public static boolean player(String p) {
        return Bukkit.getPlayer(p) != null;
    }

    /**
     * Returns if a player online with the specified name. Also sends error messages.
     * @param p The player name
     * @param s The sender to send error message
     * @return If the player is online
     */
    public static boolean player(String p, CommandSender s) {
        if (Bukkit.getPlayer(p) == null) {
            s.sendMessage(ChatColor.RED + "The player specified isn't online!");
            return false;
        }
        return true;
    }

    /**
     * Returns if a player exists with the specified name
     * @param p The player name
     * @return If the player exists
     */
    public static boolean offlinePlayer(String p) {
        return Bukkit.getOfflinePlayer(p) != null;
    }

    /**
     * Returns if a player exists with the specified name. Also sends error messages.
     * @param p The player name
     * @param s The sender to send error message
     * @return If the player exists
     */
    public static boolean offlinePlayer(String p, CommandSender s) {
        if (Bukkit.getOfflinePlayer(p) == null) {
            s.sendMessage(ChatColor.RED + "Cannot find the specified player!");
            return false;
        }
        return true;
    }

    /**
     * Returns if the object is not null.
     * @param obj The object to check
     * @return If the object is not null
     */
    public static boolean nonNull(Object obj) {
        return !(obj == null);
    }

    /**
     * Returns if the object is not null. Else throw an error.
     * @param obj The object to check
     * @return If the object is not null
     */
    public static boolean nonNullThrow(Object... obj) {
        for (Object x : obj)
            if (x == null) throw new IllegalArgumentException("Cannot be null");
        return true;
    }

    /**
     * Throws an error if the boolean is false.
     * @param bool The boolean.
     */
    public static void boolThrow(boolean bool) {
        if (!bool) throw new IllegalArgumentException("Must be true");
    }

    /**
     * Throws an error if the boolean is false.
     * @param bool The boolean.
     * @param msg  The message in the error.
     */
    public static void boolThrow(boolean bool, String msg) {
        if (!bool) throw new IllegalArgumentException(msg);
    }
}
