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

package me.aberdeener.clearchat.commands.socialspy;

import me.aberdeener.clearchat.ClearChat;
import me.aberdeener.clearchat.utils.CommandX;
import me.aberdeener.clearchat.utils.TabCompletion;
import me.aberdeener.clearchat.utils.Validate;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SocialSpyCommand extends CommandX {
    public SocialSpyCommand() {
        super("socialspy", "majorchat.socialspy");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        FileConfiguration data = ClearChat.getInstance().getData();
        if (args.length == 0) {
            if (sender instanceof ConsoleCommandSender) {
                boolean b;
                if (!data.contains("socialspy.console"))
                    b = true;
                else
                    b = !data.getBoolean("socialspy.console");

                data.set("socialspy.console", b);
                ClearChat.getInstance().saveConfig();

                if (b) sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "enabled" + ChatColor.GREEN + ".");
                else sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "disabled" + ChatColor.GREEN + ".");
            } else {
                Player player = (Player) sender;
                boolean b;
                if (!data.contains("socialspy." + player.getUniqueId().toString()))
                    b = true;
                else
                    b = !data.getBoolean("socialspy." + player.getUniqueId().toString());

                data.set("socialspy." + player.getUniqueId().toString(), b);
                ClearChat.getInstance().saveConfig();

                if (b) sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "enabled" + ChatColor.GREEN + ".");
                else sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "disabled" + ChatColor.GREEN + ".");
            }
        } else {
            if (Arrays.asList("on", "off").contains(args[0])) {
                if (sender instanceof ConsoleCommandSender) {
                    if (args[0].equals("on")) {
                        sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "enabled" + ChatColor.GREEN + ".");
                        data.set("socialspy.console", true);
                        ClearChat.getInstance().saveConfig();
                    } else if (args[0].equals("off")) {
                        sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "disabled" + ChatColor.GREEN + ".");
                        data.set("socialspy.console", false);
                        ClearChat.getInstance().saveConfig();
                    } else {
                        super.showUsage(sender);
                    }
                } else {
                    Player player = (Player) sender;
                    if (args[0].equals("on")) {
                        sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "enabled" + ChatColor.GREEN + ".");
                        data.set("socialspy." + player.getUniqueId().toString(), true);
                        ClearChat.getInstance().saveConfig();
                    } else if (args[0].equals("off")) {
                        sender.sendMessage(ChatColor.GREEN + "Social spy " + ChatColor.YELLOW + "disabled" + ChatColor.GREEN + ".");
                        data.set("socialspy." + player.getUniqueId().toString(), false);
                        ClearChat.getInstance().saveConfig();
                    } else {
                        super.showUsage(sender);
                    }
                }
            } else {
                if (!Validate.offlinePlayer(args[0], sender)) return;
                if (args.length == 2) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    if (args[1].equals("on")) {
                        sender.sendMessage(ChatColor.GREEN + "Social spy for " + ChatColor.YELLOW + Bukkit.getOfflinePlayer(args[0]).getName() + " enabled" + ChatColor.GREEN + ".");
                        data.set("socialspy." + player.getUniqueId().toString(), true);
                        ClearChat.getInstance().saveConfig();
                    } else if (args[1].equals("off")) {
                        sender.sendMessage(ChatColor.GREEN + "Social spy for " + ChatColor.YELLOW +
                                Bukkit.getOfflinePlayer(args[0]).getName() /* Get real name */ + " disabled" + ChatColor.GREEN + ".");
                        data.set("socialspy." + player.getUniqueId().toString(), false);
                        ClearChat.getInstance().saveConfig();
                    } else {
                        super.showUsage(sender);
                    }
                } else {
                    boolean b;
                    OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
                    if (!data.contains("socialspy." + player.getUniqueId().toString()))
                        b = true;
                    else
                        b = !data.getBoolean("socialspy." + player.getUniqueId().toString());

                    data.set("socialspy." + player.getUniqueId().toString(), b);
                    ClearChat.getInstance().saveConfig();

                    if (b) sender.sendMessage(ChatColor.GREEN + "Social spy for " + ChatColor.YELLOW + player.getName() + " enabled" + ChatColor.GREEN + ".");
                    else sender.sendMessage(ChatColor.GREEN + "Social spy for " + ChatColor.YELLOW + player.getName() + " disabled" + ChatColor.GREEN + ".");
                }
            }
        }
    }

    public static boolean playerSocialSpy(OfflinePlayer p) {
        FileConfiguration data = ClearChat.getInstance().getData();
        if (!data.contains("socialspy." + p.getUniqueId().toString()))
            return false;
        return data.getBoolean("socialspy." + p.getUniqueId().toString());
    }

    public static boolean consoleSocialSpy() {
        FileConfiguration data = ClearChat.getInstance().getData();
        if (!data.contains("socialspy.console"))
            return false;
        return data.getBoolean("socialspy.console");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1 || args.length == 2)
            return TabCompletion.bestMatch(args[0], Arrays.asList("on", "off"));
        return Collections.emptyList();
    }
}
