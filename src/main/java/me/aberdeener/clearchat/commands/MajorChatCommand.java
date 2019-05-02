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

package me.aberdeener.clearchat.commands;

import me.aberdeener.clearchat.ClearChat;
import me.aberdeener.clearchat.utils.CommandX;
import me.aberdeener.clearchat.utils.TabCompletion;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MajorChatCommand extends CommandX {
    public MajorChatCommand() {
        super("majorchat", "majorchat.maincommand", 1);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!Arrays.asList("reload", "version").contains(args[0])) {
            super.showUsage(sender);
            return;
        }

        if (args[0].equalsIgnoreCase("reload")) {
            ClearChat.getInstance().reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "Successfully reloaded MajorChat!");
        } else if (args[0].equalsIgnoreCase("version")) {
            sender.sendMessage(ChatColor.GREEN + "You are using MajorChat version " + ChatColor.YELLOW + ClearChat.getInstance().getDescription().getVersion() + ChatColor.GREEN + "!");
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return TabCompletion.bestMatch(args[0], Arrays.asList("reload", "version"));
        return Collections.emptyList();
    }
}
