package me.codedred.xpbottles.versions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VersionData {
	
	public boolean hasValue(ItemStack item);
	public ItemStack getBottle(Player player, int exp);
	public int getExpAmount(ItemStack item);

}
