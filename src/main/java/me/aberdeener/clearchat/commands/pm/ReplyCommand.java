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
