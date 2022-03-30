package me.codedred.xpbottles.versions;

import me.codedred.xpbottles.Main;
import net.minecraft.server.v1_16_R1.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.attribute.AttributeModifier.Operation;
import org.bukkit.craftbukkit.v1_16_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Version_v1_16_R1 implements VersionData {

    private final Main plugin;

    private final String displayName;
    private final List<String> lore;
    private final boolean glow;
    private final UUID uuid;

    public Version_v1_16_R1(Main plugin) {
        this.plugin = plugin;
        displayName = plugin.getConfig().getString("bottle.name");
        lore = plugin.getConfig().getStringList("bottle.lore");
        glow = plugin.getConfig().getBoolean("bottle.glow");
        this.uuid = (plugin.getConfig().getBoolean("use-static-uuid.enabled"))
                ? UUID.fromString(plugin.getConfig().getString("use-static-uuid.do-not-edit-this"))
                : UUID.randomUUID();
    }

    public boolean hasValue(ItemStack item) {
        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        return nmsItem.hasTag();
    }

    // TODO update this?
    public int getExpAmount(ItemStack item) {
        net.minecraft.server.v1_16_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
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
