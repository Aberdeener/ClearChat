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
import me.aberdeener.clearchat.utils.TabCompletion;
import me.aberdeener.clearchat.utils.Validate;
import me.aberdeener.clearchat.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class TellCommand extends CommandX {
    public TellCommand() {
        super("tell", "majorchat.msg", 2);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Validate.player(args[0], sender)) return;
        Player player = Bukkit.getPlayer(args[0]);
        StringBuilder msg = new StringBuilder();
        args[0] = null;

        for (String x : args) {
            if (x == null) continue;
            msg.append(x);
            msg.append(" ");
        }

        String message = msg.toString();
        final String layout;
        if (sender instanceof ConsoleCommandSender) {
            layout = ChatColor.translateAlternateColorCodes('&', ClearChat.getInstance().getConfig().getString("private-messaging.format")
                    .replace("%from%", "Console")
                    .replace("%to%", VaultUtils.getFullName(player))
                    .replace("%message%", message));
        } else {
            layout = ChatColor.translateAlternateColorCodes('&', ClearChat.getInstance().getConfig().getString("private-messaging.format")
                    .replace("%from%", VaultUtils.getFullName(((Player) sender)))
                    .replace("%to%", VaultUtils.getFullName(player))
                    .replace("%message%", message));
        }
        sender.sendMessage(layout);
        player.sendMessage(layout);
        Bukkit.getOnlinePlayers().forEach((x) -> {
            if (ClearChat.getInstance().getData().getBoolean("socialspy." + x.getUniqueId().toString(), false))
                x.sendMessage(ChatColor.GOLD + "[SocialSpy] " + ChatColor.RESET + layout);
        });
        if (ClearChat.getInstance().getData().getBoolean("socialspy.console", false))
            Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "[SocialSpy] " + ChatColor.RESET + layout);

        if (sender instanceof ConsoleCommandSender)
            ClearChat.getInstance().getData().set("tell.console.lastreci", player.getUniqueId().toString());
        else
            ClearChat.getInstance().getData().set("tell." + ((Player) sender).getUniqueId().toString() + ".lastreci", player.getUniqueId().toString());
        ClearChat.getInstance().saveConfig();
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1) return TabCompletion.allOnlinePlayers(args[0]);
        return Collections.emptyList();
    }
}
