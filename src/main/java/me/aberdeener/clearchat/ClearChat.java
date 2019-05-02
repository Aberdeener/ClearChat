package me.aberdeener.clearchat;

import me.aberdeener.clearchat.commands.pm.ReplyCommand;
import me.aberdeener.clearchat.commands.socialspy.SocialSpyCommand;
import me.aberdeener.clearchat.commands.pm.TellCommand;
import me.aberdeener.clearchat.formatter.ChatFormatterListener;
import me.aberdeener.clearchat.joinleave.JoinLeaveMessages;
import me.aberdeener.clearchat.utils.configuration.Configuration;
import me.aberdeener.clearchat.utils.configuration.ConfigurationManager;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.logging.Logger;

public class ClearChat extends JavaPlugin {

	private static ClearChat instance;
	private Configuration config;
	private Configuration data;
	public static Permission permission;
	public static Chat chat;

	public static ClearChat getInstance() {
		return instance;
	}

	private void regEvent(Listener listener) {
		getServer().getPluginManager().registerEvents(listener, this);
	}

	public void onEnable() {
		instance = this;
		// @Aberdeener
		// Please notice that do not use saveConfig().
		// Configuration is meant to be adapt by the player, and should not be saved.
		// If it's saved, then all the comments will be gone.
		// Consider creating data.yml for data storage like below if required.
		ConfigurationManager manager = new ConfigurationManager();
		config = manager.loadConfigFromResource("config.yml");
		data = manager.loadConfigFromResource("data.yml");

		Logger logger = getLogger();
		if (!getServer().getPluginManager().isPluginEnabled("Vault")) {
			logger.severe("Vault not present! Disabling ...");
			getServer().getPluginManager().disablePlugin(this);
		} else {
			logger.info("Vault found! Hooking ...");
			try {
				permission = getServer().getServicesManager().getRegistration(Permission.class).getProvider();
				chat = getServer().getServicesManager().getRegistration(Chat.class).getProvider();

				logger.info("Successfully hooked into Vault!");
			} catch (NullPointerException e) {
				logger.severe("Failed to hook into Vault! Disabling ...");
				getServer().getPluginManager().disablePlugin(this);
			}
		}

		new SocialSpyCommand();
		new TellCommand();
		new ReplyCommand();

		regEvent(new ChatFormatterListener());
		regEvent(new JoinLeaveMessages());

		logger.info("MajorChat enabled!");
	}

	@Override
	public FileConfiguration getConfig() {
		return config.getConfig();
	}

	public FileConfiguration getData() {
		return data.getConfig();
	}

	@Override
	public void reloadConfig() {
		try {
			config.reload();
			data.reload();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void saveConfig() {
		try {
			data.save();  // Do not add config.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
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

		
		// Message Editing (no permission message)
		// Commit by yangyang200: It's basically a bad practice to do this as it will destroy the comments.
		/*
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

		 */

		return true;

	}

}