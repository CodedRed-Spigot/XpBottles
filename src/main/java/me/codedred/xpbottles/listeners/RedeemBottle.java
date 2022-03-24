package me.codedred.xpbottles.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;

import me.codedred.xpbottles.Main;
import me.codedred.xpbottles.events.BottleClickEvent;
import me.codedred.xpbottles.events.ExpThrownEvent;

public class RedeemBottle implements Listener {

	private Main plugin;
	public RedeemBottle(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void redeem(BottleClickEvent event) {
		int value = plugin.bottle.getExpAmount(event.getBottle());
		int amount = event.getBottle().getAmount();
		
		Player player = event.getPlayer();
		
		//plugin.manager.setTotalExperience(player, plugin.manager.getTotalExperience(player) + value);
	    for (String message : plugin.msg.getConfig().getStringList("messages.redeem")) 
	    	player.sendMessage(plugin.f(message.replace("%exp%", Integer.toString(value))));
	    
	    
	    if (amount > 1 )
	    	event.getBottle().setAmount(amount - 1);
	    else  {
	    	if (event.isOffHand())
	    		player.getInventory().setItemInOffHand(null);
	    	else
	    		player.getInventory().removeItem(new ItemStack[] { event.getBottle() });
	    }
	    player.updateInventory();
	    if (plugin.getConfig().getBoolean("redeem-sound.enabled") == true) {
			player.playSound(player.getLocation(), Sound.valueOf(plugin.getConfig().getString("redeem-sound.sound")), 3.0F, 1.0F);	
		}

		ThrownExpBottle ex = player.launchProjectile(ThrownExpBottle.class);
		Bukkit.getPluginManager().callEvent(new ExpThrownEvent(player, new ExpBottleEvent(ex, 0), true, value));
	}
}
