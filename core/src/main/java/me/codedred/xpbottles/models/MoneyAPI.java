package me.codedred.xpbottles.models;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class MoneyAPI {

	private Economy eco;

	public Economy getEconomy() {
		return eco;
	}

	public boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = Bukkit.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			eco = economyProvider.getProvider();
		}
		return (eco != null);
	}

	public boolean isEnabled() {
		return (eco != null);
	}

}
