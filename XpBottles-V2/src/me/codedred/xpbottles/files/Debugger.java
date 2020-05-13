package me.codedred.xpbottles.files;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import me.codedred.xpbottles.Main;

public class Debugger {

	private Main plugin;
	public Debugger(Main instance) {
		plugin = instance;
	}
	public void checkText() {
		
		FileConfiguration LANG = plugin.getLang();
		FileConfiguration CONFIG = plugin.getConfig();
		
		int total = 0;
		// CONFIG
		if (!CONFIG.contains("bottle.tax")) {
			CONFIG.set("bottle.tax.enabled", false);
			CONFIG.set("bottle.tax.percent", 5);
			CONFIG.set("bottle.cost.enabled", false);
			CONFIG.set("bottle.cost.amount", 100);
			plugin.saveConfig();
			total++;
		}
		if (!CONFIG.contains("custom-drop-name")) {
			CONFIG.set("custom-drop-name.enabled", true);
			CONFIG.set("custom-drop-name.name", "&d&lExp Bottle");
			plugin.saveConfig();
			total++;
		}
		
		
		// MESSAGES
		if (!LANG.contains("messages.inventory-is-full")) {
			LANG.set("messages.inventory-is-full", "&cYour inventory is full! Cannot create bottle.");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.other-players-inventory-is-full")) {
			LANG.set("messages.other-players-inventory-is-full", "&c%player%'s inventory is full! Cannot create bottle.");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.exp-is-zero")) {
			LANG.set("messages.exp-is-zero", "&cYou cannot withdraw 0 exp!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.not-enough-exp")) {
			LANG.set("messages.not-enough-exp", "&cYou must have atleast %min% exp to create a bottle!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.cannot-afford")) {
			LANG.set("messages.cannot-afford", "&cYou do not have enough money to do this!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.no-permission")) {
			LANG.set("messages.no-permission", "&cYou do not have permission to do that!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.player-not-found")) {
			LANG.set("messages.player-not-found", "&cPlayer not found.");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.incorrect-withdrawal")) {
			LANG.set("messages.incorrect-withdrawal", "&cYou cannot withdraw that.");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.min-max-error")) {
			LANG.set("messages.min-max-error", "&cExp amount must be between &a%min% &cand &a%max%&c!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.disabled-crafting")) {
			LANG.set("messages.disabled-crafting", "&cThis item cannot be used in a crafting table!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.disable-villager-trade")) {
			LANG.set("messages.disable-villager-trade", "&cThis item cannot be traded!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.admin-bottle-sent")) {
			LANG.set("messages.admin-bottle-sent", "&cServer sent an exp bottle of &b%exp% to &b%player%&c!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.exp-reset")) {
			LANG.set("messages.exp-reset", "&cExp was reset!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.exp-taken")) {
			LANG.set("messages.exp-taken", "&cExp was taken!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.exp-given")) {
			LANG.set("messages.exp-given", "&bExp was given!");
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.redeem")) {
			LANG.createSection("messages.redeem");
			List<String> l = new ArrayList<String>();
			l.add("&9&l+ %exp%");
			l.add("&bSuccessfully added exp!");
			LANG.set("messages.redeem", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.withdraw")) {
			LANG.createSection("messages.withdraw");
			List<String> l = new ArrayList<String>();
			l.add("&c&l- %exp%");
			l.add("&4Successfully withdrew exp!");
			LANG.set("messages.withdraw", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.withdrawal-item-dropped")) {
			LANG.createSection("messages.withdrawal-item-dropped");
			List<String> l = new ArrayList<String>();
			l.add("&c&l- %exp%");
			l.add("&cWARNING: &aInventory was full, exp bottle was dropped below you!");
			LANG.set("messages.withdrawal-item-dropped", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.given-withdraw")) {
			LANG.createSection("messages.given-withdraw");
			List<String> l = new ArrayList<String>();
			l.add("&4%signer% sent you an exp bottle of &b&l%exp%!");
			LANG.set("messages.given-withdraw", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.given-withdrawal-item-dropped")) {
			LANG.createSection("messages.given-withdrawal-item-dropped");
			List<String> l = new ArrayList<String>();
			l.add("&4%signer% sent you an exp bottle of &b&l%exp%!");
			l.add("&cWARNING: &aInventory was full, exp bottle was dropped below you!");
			LANG.set("messages.given-withdrawal-item-dropped", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.exp-usage")) {
			LANG.createSection("messages.exp-usage");
			List<String> l = new ArrayList<String>();
			l.add("&b&m========&7[&9&lXpBottles&7]&b&m========");
			l.add("&d/&7exp withdraw <exp>");
			l.add("&d/&7exp withdraw all");
			l.add("&d/&7exp withdraw random");
			l.add("&d/&7exp withdraw give <exp> <player>");
			LANG.set("messages.exp-usage", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.check-exp")) {
			LANG.createSection("messages.check-exp");
			List<String> l = new ArrayList<String>();
			l.add("&6You have &c%exp%&6 exp (&c%levels%&6 levels), you need &c%exp-needed%&6 exp to level up!");
			LANG.set("messages.check-exp", l);
			plugin.msg.saveConfig();
			total++;
		}
		if (!LANG.contains("messages.check-other-exp")) {
			LANG.createSection("messages.check-other-exp");
			List<String> l = new ArrayList<String>();
			l.add("&c%player%&6 has &c%exp%&6 exp (&c%levels%&6 levels), %player% needs &c%exp-needed%&6 exp to level up!");
			LANG.set("messages.check-other-exp", l);
			plugin.msg.saveConfig();
			total++;
		}

		if (total >= 1) {
			plugin.getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + "[XpBottles] " +  ChatColor.WHITE + "Configs were updated!");
		}
	}
}
