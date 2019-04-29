package me.aberdeener.clearchat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ClearChat extends JavaPlugin {

	public void onEnable() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {

		//Clear Chat
		if (commandLabel.equalsIgnoreCase("clearchat") || commandLabel.equalsIgnoreCase("cc")) {
			//Console Sender Check
			if (! (sender instanceof Player)) {
				sender.sendMessage("[ClearChat] Please execute from in-game!");
				return false;
			}
			//Permission Check
			Player p = (Player) sender;
			if (!p.hasPermission("clearchat.clear")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-permission")));
			} else {
				for (int i = 0; i < 200; i++) {
					Bukkit.broadcastMessage("");
				}
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("chat-cleared")));
			}
		}

		
		//Message Editing (no permission message)
		if (commandLabel.equalsIgnoreCase("ccmsgnoperm") || commandLabel.equalsIgnoreCase("ccmnp")) {
			//Console Sender Check
			if (! (sender instanceof Player)) {
				sender.sendMessage("[ClearChat] Please execute from in-game!");
				return false;
			}
			//Permission Check
			Player p = (Player) sender;
			if (!p.hasPermission("clearchat.config")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-permission")));
			} else {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Please specify a message!");
					return true;
				}

				StringBuilder str = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					str.append(args[i] + " ");
				}

				//Save message to config
				String ccmsgnoperm = str.toString();
				getConfig().set("no-permission", ccmsgnoperm);
				saveConfig();
				sender.sendMessage(ChatColor.GREEN + "No permission message set to: " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-permission")));
				return true;

			}

			return true;

		}

		//Message Editing (cleared message)
		if (commandLabel.equalsIgnoreCase("ccmsgcleared") || commandLabel.equalsIgnoreCase("ccmc")) {
			//Console Sender Check
			if (! (sender instanceof Player)) {
				sender.sendMessage("[ClearChat] Please execute from in-game!");
				return false;
			}
			//Permission Check
			Player p = (Player) sender;
			if (!p.hasPermission("clearchat.config")) {
				p.sendMessage(ChatColor.translateAlternateColorCodes('&', getConfig().getString("no-permission")));
			} else {
				if (args.length == 0) {
					sender.sendMessage(ChatColor.RED + "Please specify a message!");
					return true;
				}

				StringBuilder str = new StringBuilder();
				for (int i = 0; i < args.length; i++) {
					str.append(args[i] + " ");
				}

				//Save message to config
				String ccmsgc = str.toString();
				getConfig().set("chat-cleared", ccmsgc);
				saveConfig();
				sender.sendMessage(ChatColor.GREEN + "Cleared chat message set to: " + ChatColor.translateAlternateColorCodes('&', getConfig().getString("chat-cleared")));
				return true;

			}

			return true;

		}

		return true;

	}

}