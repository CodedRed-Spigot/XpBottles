package me.codedred.xpbottles.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import me.codedred.xpbottles.Main;
import me.codedred.xpbottles.events.BottleClickEvent;
import me.codedred.xpbottles.events.VanillaBottleEvent;

public class PlayerInteract implements Listener {

	private Main plugin;
	public PlayerInteract(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onRedeem(PlayerInteractEvent event) {
		if (!event.hasItem()) return;
		if (!event.getItem().hasItemMeta()) {
			if (plugin.isNewerVersion()) {
				if (!event.getItem().getType().equals(Material.matchMaterial("EXPERIENCE_BOTTLE")))
					return;
			}
			else {
				if (!event.getItem().getType().equals(Material.matchMaterial("EXP_BOTTLE")))
					return;
			}
			event.setCancelled(true);
			boolean offHand = false;
			if (!Bukkit.getVersion().contains("1.8")) {
				if (event.getItem().equals(event.getPlayer().getInventory().getItemInOffHand()))
					offHand = true;
			}
			Bukkit.getPluginManager().callEvent(new VanillaBottleEvent(event.getPlayer(), event.getItem(), offHand));
			return;
		}
		if (plugin.isNewerVersion()) {
			if (!event.getItem().getType().equals(Material.matchMaterial("EXPERIENCE_BOTTLE")))
				return;
		}
		else {
			if (!event.getItem().getType().equals(Material.getMaterial("EXP_BOTTLE")))
				return;
		}
		if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK)
			return;
		if (!plugin.bottle.hasValue(event.getItem()))
			return;		
		boolean offHand = false;
		if (!Bukkit.getVersion().contains("1.8")) {
			if (event.getItem().equals(event.getPlayer().getInventory().getItemInOffHand()))
				offHand = true;
		}
		
		event.setCancelled(true);
	
		
		Bukkit.getPluginManager().callEvent(new BottleClickEvent(event.getPlayer(), event.getItem(), offHand));
	}

}
