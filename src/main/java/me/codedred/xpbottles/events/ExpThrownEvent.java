package me.codedred.xpbottles.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.ExpBottleEvent;

public class ExpThrownEvent extends Event implements Cancellable {
	
    private final Player player;
    private boolean isCancelled;
    private final ExpBottleEvent event;
    private final boolean custom;
    private final int value;
	
	public ExpThrownEvent(Player player, ExpBottleEvent event, boolean custom, int value) {
		this.player = player;
		this.event = event;
		this.custom = custom;
		this.value = value;
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
    
    public ExpBottleEvent getEvent() {
    	return event;
    }
    
    public boolean isCustom() {
    	return custom;
    }
    
    public int getValue() {
    	return value;
    }
    

}