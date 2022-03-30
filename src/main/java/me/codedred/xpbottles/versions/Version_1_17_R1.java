package me.codedred.xpbottles.versions;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.codedred.xpbottles.Main;
import net.minecraft.nbt.NBTTagCompound;



public class Version_1_17_R1 implements VersionData {

	private Main plugin;
	
	private String displayName;
	private List<String> lore = new ArrayList<String>();
	private boolean glow;
	private UUID uuid;
	
	public Version_1_17_R1(Main plugin) {
		this.plugin = plugin;
		displayName = plugin.getConfig().getString("bottle.name");
		lore = plugin.getConfig().getStringList("bottle.lore");
		glow = plugin.getConfig().getBoolean("bottle.glow");
		if (plugin.getConfig().getBoolean("use-static-uuid.enabled"))
			this.uuid = UUID.fromString(plugin.getConfig().getString("use-static-uuid.do-not-edit-this"));
		else
			this.uuid = UUID.randomUUID();
	}
	
	
	public boolean hasValue(ItemStack item) { 
		net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		if (nmsItem.hasTag())
			return true;
		return false;
	}
	
	// TODO update this?
	public int getExpAmount(ItemStack item) {
		net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return ((NBTTagCompound) compound.getList("AttributeModifiers", 10).get(0)).getInt("Amount");
	
	}
	
	
	public ItemStack getBottle(Player player, int exp) {
		ItemStack item = getItemStack(player, exp);
		ItemMeta meta = item.getItemMeta();
		
		AttributeModifier modifier = new AttributeModifier(uuid,
				"generic.flyingSpeed", exp, Operation.ADD_NUMBER, EquipmentSlot.HAND);
		meta.addAttributeModifier(Attribute.GENERIC_FLYING_SPEED, modifier);
		
		item.setItemMeta(meta);
		
		
		return item;
	}
	
	
	private ItemStack getItemStack(Player player, int exp) {
		ItemStack item = new ItemStack(Material.getMaterial("EXPERIENCE_BOTTLE"));
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(plugin.f(displayName.replace("%signer%", player.getName()).replace("%exp%", Integer.toString(exp))));
		List<String> updatedLore = new ArrayList<String>();
		for (String l : lore)
			updatedLore.add(plugin.f(l.replace("%signer%", player.getName()).replace("%exp%", Integer.toString(exp))));
		
		meta.setLore(updatedLore);
		
		if (glow) {
         	 meta.addEnchant(Enchantment.DURABILITY, 0, true);
             meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
		}
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        item.setItemMeta(meta);
        return item;
	}
	
	
	
	
	
}
