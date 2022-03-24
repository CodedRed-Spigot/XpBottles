package me.codedred.xpbottles.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class BottleClickEvent extends Event implements Cancellable {
	
    private final Player player;
    private boolean isCancelled;
    private final ItemStack item;
    private final boolean offHand;
	
	public BottleClickEvent(Player player, ItemStack item, boolean offHand) {
		this.player = player;
		this.item = item;
		this.offHand = offHand;
	}

    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
    
    public Player getPlayer() {
        return this.player;
    }
    
    public ItemStack getBottle() {
    	return item;
    }
    
    public boolean isOffHand() {
    	return offHand;
    }
}