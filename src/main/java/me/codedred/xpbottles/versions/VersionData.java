package me.codedred.xpbottles.versions;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface VersionData {

	boolean hasValue(ItemStack item);
	ItemStack getBottle(Player player, int exp);
	int getExpAmount(ItemStack item);

}
