package me.codedred.xpbottles.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import me.codedred.xpbottles.Main;

public class Drop implements Listener {
	
	private Main plugin;
	
	public Drop(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
    public void test(PlayerDropItemEvent e) {
		if (!plugin.getConfig().getBoolean("custom-drop-name.enabled"))
			return;
        Entity entity = e.getItemDrop();
        ItemStack item = e.getItemDrop().getItemStack();
        if (item.hasItemMeta()) {
        	if(plugin.bottle.hasValue(item)) {
        		if (!item.getType().equals(Material.matchMaterial("EXPERIENCE_BOTTLE")) && !item.getType().equals(Material.getMaterial("EXP_BOTTLE")))
        			return;
  
        		entity.setCustomName(plugin.f(plugin.getConfig().getString("custom-drop-name.name")));
        		entity.setCustomNameVisible(true);
        	}
        }
    }

}
