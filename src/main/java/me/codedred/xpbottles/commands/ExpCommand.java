package me.codedred.xpbottles.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.codedred.xpbottles.Main;

public class ExpCommand implements CommandExecutor {

	private Main plugin;
	public ExpCommand(Main main) {
		plugin = main;
	}
	
	private int max = 0;
	private int min = 0;
	private int exp = 0;
	private int updated = 0;
	
	private static boolean isNum(String s) {
	    try {
	        Integer.parseInt(s);
	    } catch (NumberFormatException nfe) {
	        return false;
	    }
	    return true;
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if (label.equalsIgnoreCase("exp")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(plugin.f("&cThis command is for players only. Try,"));
				sender.sendMessage(plugin.f("&c/&fxp create <player> <exp>"));
				return true;
			}
			Player player = (Player) sender;
			if (args.length == 0) {
				if (!player.hasPermission("xp.check")) {
					player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.no-permission")));
					return true;
				}
				for (String msg : plugin.msg.getConfig().getStringList("messages.check-exp")) {
					player.sendMessage(plugin.f(msg)
							.replace("%exp%", Integer.toString(plugin.manager.getTotalExperience(player)))
							.replace("%levels%", Integer.toString(player.getLevel()))
							.replace("%exp-needed%", Integer.toString(player.getExpToLevel())
									));
				}
				return true;
			}
			if (args.length >= 1) {
				// /xp withdraw <all/give/random> <::give-exp::> <::give-player::>
				if (args[0].equalsIgnoreCase("withdraw")) {
					if (args.length >= 4) {
						if (args[1].equalsIgnoreCase("give")) {
							if (!player.hasPermission("xp.give")) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.no-permission")));
								return true;
							}
							if (!isNum(args[2])) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.incorrect-withdrawal")));
								return true;
							}
							Player target = Bukkit.getPlayer(args[3]);
							if (target == null) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.player-not-found")));
								return true;
							}
							if (target.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == false) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.msg.getConfig().getString("messages.other-players-inventory-is-full").replace("%player%", target.getName())));
								return true;
							}
							min = getMin(player);
							max = getMax(player);
							exp = Integer.parseInt(args[2]);
							if (exp < min || exp > max ) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.min-max-error")
										.replace("%min%", Integer.toString(min)).replace("%max%", Integer.toString(max))));
								return true;
							}
							if (plugin.manager.getTotalExperience(player) < exp ) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.not-enough-exp")
										.replace("%min%", Integer.toString(min)).replace("%max%", Integer.toString(max))));
								return true;
							}
							double cost = 0.0;
							if (plugin.getConfig().getBoolean("bottle.cost.enabled")) {
								if (plugin.hasVault()) {
									cost = plugin.getConfig().getDouble("bottle.cost.amount");
									if (cost > plugin.eco.getEconomy().getBalance(player)) {
										player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.cannot-afford")));
										return true;
									}
									plugin.eco.getEconomy().withdrawPlayer(player, cost);		
								}
							}
							if (target.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == true) {
								updated = plugin.manager.getTotalExperience(player) - exp;
								plugin.manager.setTotalExperience(player, updated);
								if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
									player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
								}
								for (String message : plugin.msg.getConfig().getStringList("messages.withdrawal-item-dropped")) {
			        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
			        		    }
								for (String message : plugin.msg.getConfig().getStringList("messages.given-withdrawal-item-dropped")) {
			        		          target.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost))
			        		        		  .replace("%signer%", player.getName()));
			        		    }
								double percent = 0.0;
								if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
									if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
										percent = 100.0;
									else
										percent = plugin.getConfig().getDouble("bottle.tax.percent");
									exp = (int) (exp - (exp * (percent / 100)));
								}
								player.getWorld().dropItemNaturally(player.getLocation(), plugin.bottle.getBottle(player, exp));
								return true;
							}
							updated = plugin.manager.getTotalExperience(player) - exp;
							plugin.manager.setTotalExperience(player, updated);
							if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
								player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
							}
							for (String message : plugin.msg.getConfig().getStringList("messages.withdraw")) {
		        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
		        		    }
							for (String message : plugin.msg.getConfig().getStringList("messages.given-withdraw")) {
		        		          target.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost))
		        		        		  .replace("%signer%", player.getName()));
		        		    }
							double percent = 0.0;
							if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
								if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
									percent = 100.0;
								else
									percent = plugin.getConfig().getDouble("bottle.tax.percent");
								exp = (int) (exp - (exp * (percent / 100)));
							}
							target.getInventory().addItem( plugin.bottle.getBottle(target, exp));
							target.updateInventory();
							return true;					
						}
					}
					if (args.length >= 2) {
						// /xp withdraw random
						if (args[1].equalsIgnoreCase("random")) {
							if (!player.hasPermission("xp.random")) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.no-permission")));
								return true;
							}
							if (player.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == false) {
								player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.msg.getConfig().getString("messages.inventory-is-full")));
								return true;
							}
							max = getMax(player);
							min = getMin(player);
							if (plugin.manager.getTotalExperience(player) < min ) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.not-enough-exp").replace("%min%", Integer.toString(min))).replace("%max%", Integer.toString(min)));
								return true;
							}
							exp = plugin.manager.getTotalExperience(player);
							if (exp >= max ) {
								exp =  (int) (Math.random()*(max-min)+min);
							}
							else {
								exp =  (int) (Math.random()*(exp-min)+min);
							}
							double cost = 0.0;
							if (plugin.getConfig().getBoolean("bottle.cost.enabled")) {
								if (plugin.hasVault()) {
									cost = plugin.getConfig().getDouble("bottle.cost.amount");
									if (cost > plugin.eco.getEconomy().getBalance(player)) {
										player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.cannot-afford")));
										return true;
									}
									plugin.eco.getEconomy().withdrawPlayer(player, cost);		
								}
							}
							if (player.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == true) {
								updated = plugin.manager.getTotalExperience(player) - exp;
								plugin.manager.setTotalExperience(player, updated);
								if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
									player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
								}
								for (String message : plugin.msg.getConfig().getStringList("messages.withdrawal-item-dropped")) {
			        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
			        		    }
								double percent = 0.0;
								if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
									if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
										percent = 100.0;
									else
										percent = plugin.getConfig().getDouble("bottle.tax.percent");
									exp = (int) (exp - (exp * (percent / 100)));
								}
									
								player.getWorld().dropItemNaturally(player.getLocation(), plugin.bottle.getBottle(player, exp));
								return true;
							}
							updated = plugin.manager.getTotalExperience(player) - exp;
							plugin.manager.setTotalExperience(player, updated);
							if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
								player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
							}
							for (String message : plugin.msg.getConfig().getStringList("messages.withdraw")) {
		      		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
							}
							double percent = 0.0;
							if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
								if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
									percent = 100.0;
								else
									percent = plugin.getConfig().getDouble("bottle.tax.percent");
								exp = (int) (exp - (exp * (percent / 100)));
							}
							player.getInventory().addItem(plugin.bottle.getBottle(player, exp));
							player.updateInventory();
							return true;
						}
						
						// /xp withdraw all
						if (args[1].equalsIgnoreCase("all")) {
							if (!player.hasPermission("xp.all")) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.no-permission")));
								return true;
							}
							if (player.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == false) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.inventory-is-full")));
								return true;
							}
							exp = plugin.manager.getTotalExperience(player);
							if (exp <= 0 ) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.exp-is-zero")));
								return true;
							}
							min = getMin(player);
							max = getMax(player);
							if (exp < min || exp > max ) {
								player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.min-max-error")
										.replace("%min%", Integer.toString(min)).replace("%max%", Integer.toString(max))));
								return true;
							}
							double cost = 0.0;
							if (plugin.getConfig().getBoolean("bottle.cost.enabled")) {
								if (plugin.hasVault()) {
									cost = plugin.getConfig().getDouble("bottle.cost.amount");
									if (cost > plugin.eco.getEconomy().getBalance(player)) {
										player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.cannot-afford")));
										return true;
									}
									plugin.eco.getEconomy().withdrawPlayer(player, cost);	
								}
							}
							if (player.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == true) {
								plugin.manager.setTotalExperience(player, 0);
								if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
									player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
								}
								for (String message : plugin.msg.getConfig().getStringList("messages.withdrawal-item-dropped")) {
			        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
			        		    }
								double percent = 0.0;
								if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
									if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
										percent = 100.0;
									else
										percent = plugin.getConfig().getDouble("bottle.tax.percent");
									exp = (int) (exp - (exp * (percent / 100)));
								}
								player.getWorld().dropItemNaturally(player.getLocation(), plugin.bottle.getBottle(player, exp));
								return true;
							}
							plugin.manager.setTotalExperience(player, 0);
							
							if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
								player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
							}
							for (String message : plugin.msg.getConfig().getStringList("messages.withdraw")) {
		        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
		        		    }
							double percent = 0.0;
							if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
								if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
									percent = 100.0;
								else
									percent = plugin.getConfig().getDouble("bottle.tax.percent");
								exp = (int) (exp - (exp * (percent / 100)));
							}
							player.getInventory().addItem(plugin.bottle.getBottle(player, exp));
							player.updateInventory();
							return true;
							
						}
						if( (!player.hasPermission("xp.withdraw"))) {
							player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.no-permission")));
							return true;
						}
						// /xp withdraw <exp>
						if (player.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == false) {
							player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.msg.getConfig().getString("messages.inventory-is-full")));
							return true;
						}
						if (!isNum(args[1])) {
							player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.incorrect-withdrawal")));
							return true;
						}
						min = getMin(player);
						max = getMax(player);
						exp = Integer.parseInt(args[1]);
						
						if (exp < min || exp > max ) {
							sender.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.min-max-error")
									.replace("%min%", Integer.toString(min)).replace("%max%", Integer.toString(max))));
							return true;
						}
						//sender.sendMessage(plugin.manager.getTotalExperience(player) + "");
						if (plugin.manager.getTotalExperience(player) < exp ) {
							player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.not-enough-exp").replace("%min%", Integer.toString(min))));
							return true;
						}
						double cost = 0.0;
						if (plugin.getConfig().getBoolean("bottle.cost.enabled")) {
							if (plugin.hasVault()) {
								cost = plugin.getConfig().getDouble("bottle.cost.amount");
								if (cost > plugin.eco.getEconomy().getBalance(player)) {
									player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.cannot-afford")));
									return true;
								}
								plugin.eco.getEconomy().withdrawPlayer(player, cost);	
							}
						}
						if (player.getInventory().firstEmpty() == -1 && plugin.getConfig().getBoolean("drop-bottle.enabled") == true) {
							updated = plugin.manager.getTotalExperience(player) - exp;
							plugin.manager.setTotalExperience(player, updated);
							if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
								player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
							}
							for (String message : plugin.msg.getConfig().getStringList("messages.withdrawal-item-dropped")) {
		        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
		        		    }
							double percent = 0.0;
							if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
								if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
									percent = 100.0;
								else
									percent = plugin.getConfig().getDouble("bottle.tax.percent");
								exp = (int) (exp - (exp * (percent / 100)));
							}
							player.getWorld().dropItemNaturally(player.getLocation(), plugin.bottle.getBottle(player, exp));
							return true;
						}
						updated = plugin.manager.getTotalExperience(player) - exp;
						plugin.manager.setTotalExperience(player, updated);

						if ( plugin.getConfig().getBoolean("withdraw-sound.enabled") == true) {
							player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("withdraw-sound.sound")), 3.0F, 1.0F);
						}
						for (String message : plugin.msg.getConfig().getStringList("messages.withdraw")) {
	        		          player.sendMessage(ChatColor.translateAlternateColorCodes('&', message).replace("%exp%", Integer.toString(exp)).replace("%cost%", Double.toString(cost)));
	        		    }
						double percent = 0.0;
						if (plugin.getConfig().getBoolean("bottle.tax.enabled")) {
							if (plugin.getConfig().getDouble("bottle.tax.percent") > 100.0)
								percent = 100.0;
							else
								percent = plugin.getConfig().getDouble("bottle.tax.percent");
							exp = (int) (exp - (exp * (percent / 100)));
						}
						player.getInventory().addItem(plugin.bottle.getBottle(player, exp));
						player.updateInventory();
						return true;
					}
					for (String msg : plugin.msg.getConfig().getStringList("messages.exp-usage")) {
						player.sendMessage(plugin.f(msg));
					}
					return true;
				}
				else {
					// /exp <player>
					if (player.hasPermission("xp.check-others")) {
						Player target = Bukkit.getPlayer(args[0]);
						if (target == null) {
							player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.player-not-found")));
							return true;
						}
						for (String msg : plugin.msg.getConfig().getStringList("messages.check-other-exp")) {
							player.sendMessage(plugin.f(msg)
									.replace("%player%", target.getName())
									.replace("%exp%", Integer.toString(plugin.manager.getTotalExperience(target)))
									.replace("%levels%", Integer.toString(target.getLevel()))
									.replace("%exp-needed%", Integer.toString(target.getExpToLevel())
											));
						}
						return true;
					}
					for (String msg : plugin.msg.getConfig().getStringList("messages.exp-usage")) {
						player.sendMessage(plugin.f(msg));
					}
					return true;
				}
			}
			
			
		}
		return false;
	}
	
	private int getMax(Player player) {
		if (plugin.getConfig().getBoolean("use-perm-based-exp") == true ) {
			for (String perm : plugin.getConfig().getConfigurationSection("permission").getKeys(false)) {
				if (player.hasPermission("xp." + perm) == true ) {
					return  plugin.getConfig().getInt("permission." + perm + ".max-bottle-amount");
				}
			}
		}

			return  plugin.getConfig().getInt("max-bottle-amount");
	}
	
	private int getMin(Player player) {
		if (plugin.getConfig().getBoolean("use-perm-based-exp") == true ) {
			for (String perm : plugin.getConfig().getConfigurationSection("permission").getKeys(false)) {
				if (player.hasPermission("xp." + perm) == true ) {
					return  plugin.getConfig().getInt("permission." + perm + ".min-bottle-amount");
				}
			}
		}

			return  plugin.getConfig().getInt("min-bottle-amount");
	}
	
	

}
