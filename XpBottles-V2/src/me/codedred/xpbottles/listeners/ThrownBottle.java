package me.codedred.xpbottles.listeners;

import java.util.Random;

import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.ThrownExpBottle;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ExpBottleEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import me.codedred.xpbottles.events.ExpThrownEvent;

public class ThrownBottle implements Listener {
	
	@EventHandler
	public void onThrown(ExpThrownEvent event) {
		if (event.isCustom())
			event.getEvent().getEntity().setCustomName("xpb-" + Integer.toString(event.getValue()));
	}
	
	@EventHandler
	public void bottleExplode(ProjectileHitEvent event) {
		if (event.getEntity() instanceof ThrownExpBottle) {
			if (event.getEntity().getCustomName() != null) {
				if (event.getEntity().getCustomName().contains("xpb-")) {
					int value = Integer.parseInt(event.getEntity().getCustomName().replace("xpb-", ""));
					event.getEntity().getWorld().spawn(event.getEntity().getLocation(), ExperienceOrb.class).setExperience(value);
					return;
				}
			}
			Random r = new Random();
			event.getEntity().getWorld().spawn(event.getEntity().getLocation(), ExperienceOrb.class).setExperience(r.nextInt(11 - 3 + 1) + 3);
		}
		
	}
	
	@EventHandler
	public void normalBottle(ExpBottleEvent event) {
		event.setExperience(0);
		

	}

}
