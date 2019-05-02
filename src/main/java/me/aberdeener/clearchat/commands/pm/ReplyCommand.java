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

package me.aberdeener.clearchat.commands.pm;

import me.aberdeener.clearchat.ClearChat;
import me.aberdeener.clearchat.utils.CommandX;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.UUID;

public class ReplyCommand extends CommandX {
    public ReplyCommand() {
        super("reply", "majorchat.msg", 1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        StringBuilder msg = new StringBuilder();
        for (String x : args) {
            msg.append(x);
            msg.append(" ");
        }

        FileConfiguration data = ClearChat.getInstance().getData();
        if (sender instanceof ConsoleCommandSender) {
            if (!data.contains("tell.console.lastreci")) {
                sender.sendMessage(ChatColor.RED + "You have no one to reply to!");
                return;
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tell " + Bukkit.getOfflinePlayer(UUID.fromString(data.getString("tell.console.lastreci"))).getName() + " " + msg.toString());
        } else {
            if (!data.contains("tell." + ((Player) sender).getUniqueId().toString() + ".lastreci")) {
                sender.sendMessage(ChatColor.RED + "You have no one to reply to!");
                return;
            }
            Bukkit.dispatchCommand(sender, "tell " + Bukkit.getOfflinePlayer(
                    UUID.fromString(data.getString("tell." + ((Player) sender).getUniqueId().toString() + ".lastreci"))
            ).getName() + " " + msg.toString());
        }
    }
}
