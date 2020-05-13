package me.codedred.xpbottles.models;

import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;


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
}
