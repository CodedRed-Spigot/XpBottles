package me.codedred.xpbottles.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import me.codedred.xpbottles.Main;

public class Reject implements Listener {
	
	private Main plugin;
	public Reject(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	 public void onInventoryClick(InventoryClickEvent event) {
		    Player player;
		    player = (Player)event.getWhoClicked();
		      ItemStack item = event.getCurrentItem();
		      	try {
	        		if ((plugin.getConfig().getBoolean("reject-crafting") == true) && 
	        		        (event.getWhoClicked().getOpenInventory().getTopInventory().getType() == InventoryType.WORKBENCH) && 
	        		        (item.hasItemMeta()) && (item.getItemMeta().hasDisplayName()) && 
	        		        (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(plugin.getConfig().getString("bottle.name")
	        		        		.replace("&1", "").replace("&4", "").replace("&c", "")
	        		        		.replace("&6", "").replace("&e", "").replace("&2", "")
	        		        		.replace("&a",  "").replace("&b", "").replace("&3", "")
	        		        		.replace("&9", "").replace("&d", "").replace("&5", "")
	        		        		.replace("&f", "").replace("&0", "").replace("&7", "")
	        		        		.replace("&8", "").replace("&l", "").replace("&m", "").replace("&o", "")
	        		        		.replace("&k", "")))) {
	        			player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.disabled-crafting")));
	        			event.setCancelled(true);
	        			return;
	        		}

	        		if ((plugin.getConfig().getBoolean("reject-villager-trading", true)) && 
	        		        (event.getWhoClicked().getOpenInventory().getTopInventory().getType() == InventoryType.MERCHANT) && 
	        		        (item.hasItemMeta()) && (item.getItemMeta().hasDisplayName()) && 
	        		        (ChatColor.stripColor(event.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase(plugin.getConfig().getString("bottle.name")
	        		        		.replace("&1", "").replace("&4", "").replace("&c", "")
	        		        		.replace("&6", "").replace("&e", "").replace("&2", "")
	        		        		.replace("&a",  "").replace("&b", "").replace("&3", "")
	        		        		.replace("&9", "").replace("&d", "").replace("&5", "")
	        		        		.replace("&f", "").replace("&0", "").replace("&7", "")
	        		        		.replace("&8", "").replace("&l", "").replace("&m", "").replace("&o", "")
	        		        		.replace("&k", "")))) {
	        			player.sendMessage(plugin.f(plugin.msg.getConfig().getString("messages.disable-villager-trade")));
	        			event.setCancelled(true);
	        			return;
	        		}
		      	} catch (Exception e) {
		      		return;
		      	}
	    }

}
