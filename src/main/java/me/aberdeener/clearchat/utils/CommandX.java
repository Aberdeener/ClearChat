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

package me.aberdeener.clearchat.utils;

import me.aberdeener.clearchat.ClearChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public abstract class CommandX implements CommandExecutor, TabCompleter, Comparable<CommandX> {
    private static List<CommandX> commands;
    protected ClearChat plugin;
    protected String perm;
    protected String command;
    protected int minArgs;
    protected int namePos;
    protected PluginCommand cmd;
    protected boolean playerOnly;

    static {
        CommandX.commands = new ArrayList<>();
    }

    /**
     * Get all commands that are registered.
     *
     * @return An array of commands
     */
    public static List<CommandX> getCommands() {
        return CommandX.commands;
    }

    /**
     * Compare to another CommandX instance.
     * @param skelly Another CommandX instance.
     * @return       -1, 1 or 0.
     */
    @Override
    public int compareTo(final CommandX skelly) {
        return this.cmd.getUsage().compareToIgnoreCase(skelly.cmd.getUsage());
    }

    /**
     * Check if this instance is equal to another CommandX instance.
     * @param o Another CommandX instance.
     * @return  true for equals, false for else.
     */
    @Override
    public boolean equals(final Object o) {
        return o.getClass() == this.getClass();
    }

    @Override
    public int hashCode() {
        return this.getClass().hashCode();
    }

    /**
     * Default constructor with 0 as minArgs.
     *
     * @param command The command in string
     * @param perm    The permission required to execute this command
     */
    public CommandX(final String command, final String perm) {
        this(command, perm, 0);
    }

    /**
     * Another constructor that is mainly used.
     *
     * @param command The command in string
     * @param perm    The permission required to execute this command
     * @param minArgs The minimum args required to execute this command
     */
    public CommandX(final String command, final String perm, final int minArgs) {
        this(command, perm, minArgs, false);
    }

    /**
     * Constructor that gives default value of minArgs.
     * @param command    The command in string
     * @param perm       The permission required to execute this command
     * @param playerOnly Boolean if command can only be executed by players.
     */
    public CommandX(final String command, final String perm, final boolean playerOnly) {
        this(command, perm, 0, playerOnly);
    }

    /**
     * Used to set if the command can only be executed by a player.
     *
     * @param command    The command in string
     * @param perm       The permission required to execute this command
     * @param minArgs    The minimum args required to execute this command
     * @param playerOnly Boolean if command can only be executed by players.
     */
    public CommandX(final String command, final String perm, final int minArgs, final boolean playerOnly) {
        super();
        this.plugin = ClearChat.getInstance();
        this.minArgs = minArgs;
        this.namePos = 1;
        this.perm = perm;
        this.playerOnly = playerOnly;
        this.cmd = this.plugin.getCommand(command);
        if (this.cmd != null) {
            this.cmd.setExecutor(this);
            this.cmd.setTabCompleter(this);
        }
        CommandX.commands.add(this);
    }

    /**
     * Gets the usage of the command.
     * @return The usage in human readable format.
     */
    public String getUsage() {
        return ChatColor.RED + "Usage: " + this.cmd.getUsage().replace("<command>", this.cmd.getName());
    }

    /**
     * Get the description of the command.
     * @return The description of the command.
     */
    public String getDescription() {
        return this.cmd.getDescription();
    }

    /**
     * Check if the CommandSender has a permission.
     * @param  sender The CommandSender to check for.
     * @return If the command sender has the permission.
     */
    public boolean hasPermission(final CommandSender sender) {
        if (this.perm.equalsIgnoreCase("NOPERMS"))
            return true;
        return sender instanceof ConsoleCommandSender || VaultUtils.hasPermission(sender, this.perm);
    }

    /**
     * Sends the command sender the usage
     * @param sender The sender to sender the message
     */
    public void showUsage(final CommandSender sender) {
        sender.sendMessage(this.getUsage());
    }

    /**
     * Run the command (Not recommended)
     * @param sender The CommandSender to run the command as.
     * @param args The arguments to pass in for the command.
     */
    public void run(CommandSender sender, String[] args) {
        this.onCommand(sender, this.cmd, this.cmd.getName(), args);
    }

    /**
     * Handling the command.
     * @param  sender The CommandSender
     * @param  command The Command
     * @param  label The label
     * @param  args Arguments
     * @return If the command shall show a usage message
     * @see org.bukkit.command.CommandExecutor#onCommand
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!this.hasPermission(sender)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + sender.getName() + " denied access to /" + command.getName());
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', ClearChat.getInstance().getConfig().getString("no-permission")));
            return true;
        }
        if (this.playerOnly) {
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.RED + "This command can only be executed by players!");
                return true;
            }
        }
        if (this.minArgs > 0 && args.length < this.minArgs && this.getUsage() != null) {
            sender.sendMessage(this.getUsage());
            return true;
        }
        try {
            this.execute(sender, args);
        } catch (Exception e) {
            ClearChat.getInstance().getLogger().severe("ClearChat error stack trace (Most recent command call issued by " + sender.getName() + ", with command " + label + " and args " + Arrays.toString(args) + ".");
            e.printStackTrace();
            sender.sendMessage(ChatColor.RED + "An internal error occurred while attempting to execute this command: ");
            final StringBuilder sb = new StringBuilder(args[0]);
            for (int i = 1; i < args.length; ++i)
                sb.append(" " + args[i]);
            sender.sendMessage(ChatColor.YELLOW + "/" + label + " " + sb.toString());
            if (sender instanceof ConsoleCommandSender) {
                sender.sendMessage(ChatColor.RED + "Exception: " + e.getClass().getSimpleName() + ": " + e.getMessage());
                sender.sendMessage(ChatColor.RED + "Please see the stack trace above, and consider reporting it to Aberdeener via https://github.com/Aberdeener/MajorChat/issues/new.");
            }
            if (sender instanceof Player) {
                sender.sendMessage(ChatColor.RED + "An internal error occurred while trying to execute command /" + label + " " + sb.toString());
                sender.sendMessage(ChatColor.RED + "Exception: " + ChatColor.YELLOW + e.getClass().getSimpleName() + ": " + e.getMessage());
                sender.sendMessage(ChatColor.RED + "Please consider reporting this (including stack trace) to Aberdeener via https://github.com/Aberdeener/MajorChat/issues/new.");
            }
        }
        return true;
    }

    /**
     * Returns a string representation of the object.
     * @return A string representation of the object.
     */
    @Override
    public String toString() {
        return "CommandX" +
                "(cmd=" + this.cmd + ", perm=" + this.perm + ", minArgs=" + this.minArgs + ", playerOnly=" + this.playerOnly + ")";
    }

    /**
     * Tab completion
     * @param  sender The CommandSender
     * @param  command The Command
     * @param  label The label
     * @param  args Arguments
     * @return All available tab completion items.
     * @see org.bukkit.command.TabCompleter#onTabComplete
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (this.hasPermission(sender))
            return this.tabComplete(sender, args);
        return Arrays.asList();
    }

    /**
     * Executes the command.
     * @param sender CommandSender that is executing the command.
     * @param args Arguments that are passed in.
     */
    public abstract void execute(CommandSender sender, String[] args);

    /**
     * Tab completion
     * @param  args Arguments that are passed in.
     * @return All available tab completion items.
     */
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return Collections.emptyList();
    }

    /**
     * Gets the permission
     * @return The permission
     */
    public String getPerm() {
        return this.perm;
    }

    /**
     * Gets the command label
     * @return The label
     */
    public String getCommand() {
        return this.command;
    }

    /**
     * Gets the plugin command object
     * @return The plugin command
     */
    public PluginCommand getPluginCommand() {
        return this.cmd;
    }
}

