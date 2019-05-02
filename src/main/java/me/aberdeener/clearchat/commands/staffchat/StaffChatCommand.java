package me.aberdeener.clearchat.commands.staffchat;

import me.aberdeener.clearchat.ClearChat;
import me.aberdeener.clearchat.utils.CommandX;
import me.aberdeener.clearchat.utils.MetadataValue;
import me.aberdeener.clearchat.utils.TabCompletion;
import me.aberdeener.clearchat.utils.VaultUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class StaffChatCommand extends CommandX {
    public StaffChatCommand() {
        super("sc", "majorchat.staff", 1);
    }

    public static void postStaffChat(String who, String msg) {
        final String layout = ChatColor.translateAlternateColorCodes('&', ClearChat.getInstance().getConfig().
                getString("staff-chat.format").replace("%player%", who).replace("%message%", msg));
        Bukkit.getOnlinePlayers().forEach((x) -> {
            if (VaultUtils.hasPermission(x, "majorchat.staff"))
                x.sendMessage(layout);
        });
        Bukkit.getConsoleSender().sendMessage(layout);
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (sender instanceof ConsoleCommandSender) {
            if (args[0].equals("setprefix") || args[0].equals("toggle")) {
                sender.sendMessage(ChatColor.RED + "You cannot use setprefix or toggle on console!");
            } else {
                StringBuilder msg = new StringBuilder();
                for (String x : args) {
                    msg.append(x);
                    msg.append(" ");
                }
                postStaffChat(ChatColor.GOLD + "Console", msg.toString());
            }
        } else {
            if (Arrays.asList("setprefix", "toggle").contains(args[0])) {
                Player player = (Player) sender;
                if (args[0].equals("setprefix")) {
                    if (args.length == 1 || args.length > 2) {
                        super.showUsage(sender);
                        return;
                    }

                    if (args[1].length() != 1) {
                        sender.sendMessage(ChatColor.RED + "Prefix can only be one character long!");
                        return;
                    }

                    FileConfiguration data = ClearChat.getInstance().getData();
                    data.set("staff-chat." + player.getUniqueId().toString() + ".prefix", args[1]);
                    sender.sendMessage(ChatColor.GREEN + "Set your staff chat prefix to " + ChatColor.YELLOW + args[1] + ChatColor.GREEN + "!");
                    ClearChat.getInstance().saveConfig();
                } else if (args[0].equals("toggle")) {
                    if (player.hasMetadata("inStaffChat")) {
                        player.removeMetadata("inStaffChat", ClearChat.getInstance());
                        player.sendMessage(ChatColor.GREEN + "Set staff chat status " + ChatColor.YELLOW + "disabled" + ChatColor.GREEN + ".");
                    } else {
                        player.setMetadata("inStaffChat", new MetadataValue());
                        player.sendMessage(ChatColor.GREEN + "Set staff chat status " + ChatColor.YELLOW + "enabled" + ChatColor.GREEN + ".");
                    }
                }
            } else {
                StringBuilder msg = new StringBuilder();
                for (String x : args) {
                    msg.append(x);
                    msg.append(" ");
                }
                postStaffChat(VaultUtils.getFullName(((Player) sender)), msg.toString());
            }
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        if (args.length == 1)
            return TabCompletion.bestMatch(args[0], Arrays.asList("setprefix", "toggle"));
        return Collections.emptyList();
    }
}

