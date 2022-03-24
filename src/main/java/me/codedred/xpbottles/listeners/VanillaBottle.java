package me.codedred.xpbottles.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.inventory.ItemStack;

import me.codedred.xpbottles.events.ExpThrownEvent;
import me.codedred.xpbottles.events.VanillaBottleEvent;

public class VanillaBottle implements Listener {
	
	@EventHandler
	public void onRedeem(VanillaBottleEvent event) {
		event.setCancelled(true);
		int amount = event.getBottle().getAmount();
		if (amount > 1 )
			event.getBottle().setAmount(amount - 1);
	    else  {
	    	if (event.isOffHand())
	    		event.getPlayer().getInventory().setItemInOffHand(null);
	    	else
	    		event.getPlayer().getInventory().removeItem(new ItemStack[] { event.getBottle() });
	    }
		ThrownExpBottle ex = event.getPlayer().launchProjectile(ThrownExpBottle.class);
		Bukkit.getPluginManager().callEvent(new ExpThrownEvent(event.getPlayer(), new ExpBottleEvent(ex, 0), false, 0));
	}

}
