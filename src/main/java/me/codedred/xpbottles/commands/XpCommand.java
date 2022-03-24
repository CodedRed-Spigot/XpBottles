package me.codedred.xpbottles.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.codedred.xpbottles.Main;


public class XpCommand implements CommandExecutor {

	private Main plugin;
	public XpCommand(Main plugin) {
		this.plugin = plugin;
	}
	
	private static boolean isNum(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("xp")) {
			
			if (!sender.hasPermission("plugin.manager.admin")) {
				sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.no-permission")));
				return true;
			}
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("reload")) {
					plugin.msg.reloadConfig();
					plugin.reloadConfig();
					sender.sendMessage(plugin.f("&9&lConfigs reloaded!"));
					return true;
				}
			}
			if (args.length <= 1) {
				sender.sendMessage(plugin.f("&9&lTry,"));
				sender.sendMessage(plugin.f("&9/&7xp create <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp give <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp take <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp reset <player>"));
				sender.sendMessage(plugin.f("&9/&7xp reload"));
				return true;
			}
			if (args.length == 2) {
				if (args[0].equalsIgnoreCase("reset")) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.player-not-found")));
						return true;
					}
					plugin.manager.setTotalExperience(target, 0);
					sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.exp-reset").replace("%player%", target.getName())));
					return true;
				}
				sender.sendMessage(plugin.f("&9&lTry,"));
				sender.sendMessage(plugin.f("&9/&7xp create <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp give <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp take <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp reset <player>"));
				sender.sendMessage(plugin.f("&9/&7xp reload"));
				return true;
			}
			if (args.length < 3) {
				sender.sendMessage(plugin.f("&9&lTry,"));
				sender.sendMessage(plugin.f("&9/&7xp create <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp give <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp take <player> <exp>"));
				sender.sendMessage(plugin.f("&9/&7xp reset <player>"));
				sender.sendMessage(plugin.f("&9/&7xp reload"));
				return true;
			}
			if (args.length >= 3) {
				if (args[0].equalsIgnoreCase("create")) {
					if (!isNum(args[2])) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.incorrect-withdrawal")));
						return true;
					}
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.player-not-found")));
						return true;
					}
					if (target.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == false) {
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.msg.getConfig().getString("messages.other-players-inventory-is-full").replace("%player%", target.getName())));
						return true;
					}
					int exp = Integer.parseInt(args[2]);
					if (target.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == true) {
						target.getWorld().dropItemNaturally(target.getLocation(), plugin.bottle.getBottle(target, exp));
						 sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.msg.getConfig().getString("messages.admin-bottle-sent")).replace("%player%", target.getName()).replace("%exp%", Integer.toString(exp)));
						return true;
					}
					target.getInventory().addItem(plugin.bottle.getBottle(target, exp));
					target.updateInventory();
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.msg.getConfig().getString("messages.admin-bottle-sent")).replace("%player%", target.getName()).replace("%exp%", Integer.toString(exp)));
					return true;
				}
				if (args[0].equalsIgnoreCase("take")) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.player-not-found")));
						return true;
					}
					if (!isNum(args[2])) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.incorrect-withdrawal")));
						return true;
					}
					int exp = Integer.parseInt(args[2]);
					int total = plugin.manager.getTotalExperience(target);
					if (exp > total) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.incorrect-withdrawal")));
						return true;
					}
					int update = total - exp;
					plugin.manager.setTotalExperience(target, update);
					sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.exp-taken").replace("%player%", target.getName())));
					return true;
				}
				if (args[0].equalsIgnoreCase("give")) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.player-not-found")));
						return true;
					}
					if (!isNum(args[2])) {
						sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.incorrect-withdrawal")));
						return true;
					}
					int exp = Integer.parseInt(args[2]);
					int total = plugin.manager.getTotalExperience(target);
					plugin.manager.setTotalExperience(target, (total+exp));
					sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.exp-given").replace("%player%", target.getName())));
					return true;
				}
			}
			sender.sendMessage(plugin.f("&9&lTry,"));
			sender.sendMessage(plugin.f("&9/&7xp create <player> <exp>"));
			sender.sendMessage(plugin.f("&9/&7xp give <player> <exp>"));
			sender.sendMessage(plugin.f("&9/&7xp take <player> <exp>"));
			sender.sendMessage(plugin.f("&9/&7xp reset <player>"));
			sender.sendMessage(plugin.f("&9/&7xp reload"));
			return true;
			
		}
		return false;
	}

}
