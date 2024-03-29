package me.codedred.xpbottles.versions;

import me.codedred.xpbottles.Main;
import net.minecraft.server.v1_15_R1.NBTTagByte;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import net.minecraft.server.v1_15_R1.NBTTagInt;
import net.minecraft.server.v1_15_R1.NBTTagList;
import net.minecraft.server.v1_15_R1.NBTTagString;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Version_v1_15_R1 implements VersionData {

	private final Main plugin;

	private final String displayName;
	private final List<String> lore;
	private final boolean glow;

	public Version_v1_15_R1(Main plugin) {
		this.plugin = plugin;
		displayName = plugin.getConfig().getString("bottle.name");
		lore = plugin.getConfig().getStringList("bottle.lore");
		glow = plugin.getConfig().getBoolean("bottle.glow");
	}

	public boolean hasValue(ItemStack item) {
		net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		return nmsItem.hasTag() && !nmsItem.getTag().getList("AttributeModifiers", 10).isEmpty();
	}

	public int getExpAmount(ItemStack item) {
		net.minecraft.server.v1_15_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsItem.hasTag()) ? nmsItem.getTag() : new NBTTagCompound();
		return ((NBTTagCompound) compound.getList("AttributeModifiers", 10).get(0)).getInt("Amount");
	}

	public ItemStack getBottle(Player player, int exp) {
		ItemStack item = getItemStack(player, exp);

		net.minecraft.server.v1_15_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
		NBTTagCompound compound = (nmsStack.hasTag()) ? nmsStack.getTag() : new NBTTagCompound();
		NBTTagList modifiers = new NBTTagList();
		NBTTagCompound damage = new NBTTagCompound();
		damage.set("AttributeName", NBTTagString.a("generic.flyingSpeed"));
		damage.set("Name", NBTTagString.a("generic.flyingSpeed"));
		damage.set("Amount", NBTTagInt.a(exp));
		damage.set("Operation", NBTTagInt.a(0));
		damage.set("UUIDLeast", NBTTagInt.a(894654));
		damage.set("UUIDMost", NBTTagInt.a(2872));
		damage.set("Slot", NBTTagString.a("mainhand"));
		modifiers.add(damage);
		compound.set("AttributeModifiers", modifiers);
		nmsStack.setTag(compound);
		item = CraftItemStack.asBukkitCopy(nmsStack);
		compound.set("Unbreakable", NBTTagByte.a((byte) 1));

		return item;
	}

	private ItemStack getItemStack(Player player, int exp) {
		ItemStack item = new ItemStack(Material.getMaterial("EXPERIENCE_BOTTLE"));
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
