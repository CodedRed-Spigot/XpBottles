package me.codedred.xpbottles.versions;

import me.codedred.xpbottles.Main;
import net.minecraft.server.v1_8_R3.NBTTagByte;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagInt;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Version_v1_8_R3 implements VersionData {

	private final Main plugin;

	private final String displayName;
	private final List<String> lore;
	private final boolean glow;

	public Version_v1_8_R3(Main plugin) {
		this.plugin = plugin;
		displayName = plugin.getConfig().getString("bottle.name");
		lore = plugin.getConfig().getStringList("bottle.lore");
		glow = plugin.getConfig().getBoolean("bottle.glow");
	}

	public boolean hasValue(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.hasTag();
	}

	public int getExpAmount(ItemStack item) {
		net.minecraft.server.v1_8_R3.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return compound.getList("AttributeModifiers", 10).get(0).getInt("Amount");
	}

	public ItemStack getBottle(Player player, int exp) {
		ItemStack item = getItemStack(player, exp);

		net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		NBTTagCompound damage = new NBTTagCompound();
		damage.set("AttributeName", new NBTTagString("generic.flyingSpeed"));
		damage.set("Name", new NBTTagString("generic.flyingSpeed"));
		damage.set("Amount", new NBTTagInt(exp));
		damage.set("Operation", new NBTTagInt(0));
		damage.set("UUIDLeast", new NBTTagInt(894654));
		damage.set("UUIDMost", new NBTTagInt(2872));
		damage.set("Slot", new NBTTagString("mainhand"));
		modifiers.add(damage);
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		compound.set("Unbreakable", new NBTTagByte((byte) 1));

		return item;
	}


	private ItemStack getItemStack(Player player, int exp) {
		ItemStack item = new ItemStack(Material.getMaterial("EXP_BOTTLE"));
		ItemMeta meta = item.getItemMeta();

		meta.setDisplayName(plugin.f(displayName.replace("%signer%", player.getName()).replace("%exp%", Integer.toString(exp))));

		List<String> updatedLore = new ArrayList<>();
		for (String l : lore) {
			updatedLore.add(plugin.f(l.replace("%signer%", player.getName()).replace("%exp%", Integer.toString(exp))));
		}

		meta.setLore(updatedLore);

		if (glow) {
			meta.addEnchant(Enchantment.DURABILITY, 0, true);
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		}
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

}
