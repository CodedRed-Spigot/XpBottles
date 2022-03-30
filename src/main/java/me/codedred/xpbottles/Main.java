package me.codedred.xpbottles;

import me.codedred.xpbottles.commands.ExpCommand;
import me.codedred.xpbottles.commands.XpCommand;
import me.codedred.xpbottles.commands.tabcompleter.ExpTab;
import me.codedred.xpbottles.commands.tabcompleter.XpTab;
import me.codedred.xpbottles.files.Config;
import me.codedred.xpbottles.files.Debugger;
import me.codedred.xpbottles.files.Messages;
import me.codedred.xpbottles.listeners.Drop;
import me.codedred.xpbottles.listeners.PlayerInteract;
import me.codedred.xpbottles.listeners.RedeemBottle;
import me.codedred.xpbottles.listeners.Reject;
import me.codedred.xpbottles.listeners.ThrownBottle;
import me.codedred.xpbottles.listeners.VanillaBottle;
import me.codedred.xpbottles.models.ExperienceManager;
import me.codedred.xpbottles.models.MoneyAPI;
import me.codedred.xpbottles.utils.HexUtil;
import me.codedred.xpbottles.versions.VersionData;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

public class Main extends JavaPlugin {

	private static final int SPIGOT_PROJECT_ID = 69233;
	private static final String NMS_CLASS_NAME = "me.codedred.xpbottles.versions.Version_%s";
	private static final String[] COMPATIBLE_VERSIONS = {
			"v1_8_R3",
			"v1_9_R2",
			"v1_10_R1",
			"v1_11_R1",
			"v1_12_R1",
			"v1_13_R2",
			"v1_14_R1",
			"v1_15_R1",
			"v1_16_R1",
			"v1_16_R2",
			"v1_16_R3",
			"v1_17_R1",
			"v1_18_R2",
	};

	public Config cfg;
	public Messages msg;

	public VersionData bottle;
	public MoneyAPI eco;
	public ExperienceManager manager;
	private String craftBukkitVersion;

	@Override
	public void onEnable() {
		this.cfg = new Config(this);
		this.msg = new Messages(this);
		Debugger debug = new Debugger(this);
		debug.checkText();

		if (this.getServer().getVersion().contains("1.16") || this.getServer().getVersion().contains("1.17")) {
			if (cfg.getConfig().getBoolean("use-static-uuid.enabled")) {
				if (!cfg.getConfig().contains("use-static-uuid.do-not-edit-this")) {
					this.getConfig().set("use-static-uuid.do-not-edit-this", UUID.randomUUID().toString());
					this.saveConfig();
				}
			}
		}

		if (!setupBottles()) {
			getLogger().severe("Failed to setup XpBottles!");
            getLogger().severe("Your server version is not compatible with this plugin!");
            getLogger().severe("Server version: " + craftBukkitVersion);
            getLogger().severe("Report this to CodedRed ASAP! Will be fixed within 24hrs!");
            getLogger().severe("Join Discord to report: https://discord.gg/gqwtqX3");
            getLogger().severe("Compatible versions: " + Arrays.toString(COMPATIBLE_VERSIONS));
            Bukkit.getPluginManager().disablePlugin(this);
            return;
		}

		if (hasVault()) {
			this.eco = new MoneyAPI();
			if (!eco.setupEconomy()) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.RED + "XpBottles could not connect to Vault, disable cost/tax features!");
	            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.DARK_RED + "=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
	           // Bukkit.getPluginManager().disablePlugin(this);
	            return;
			}
		}

		this.manager = new ExperienceManager();

		registerEvents();
		registerCommands();

		hasUpdate();
		@SuppressWarnings("unused")
        Metrics metrics = new Metrics(this);
	}

	private void registerEvents() {
		PluginManager manager = this.getServer().getPluginManager();
		manager.registerEvents(new PlayerInteract(this), this);
		manager.registerEvents(new RedeemBottle(this), this);
		manager.registerEvents(new Reject(this), this);
		manager.registerEvents(new Drop(this), this);
		manager.registerEvents(new ThrownBottle(), this);
		manager.registerEvents(new VanillaBottle(), this);
	}

	private void registerCommands() {
		this.getCommand("Xp").setExecutor(new XpCommand(this));
		this.getCommand("Exp").setExecutor(new ExpCommand(this));
		if (isNewerVersion()) {
			this.getCommand("exp").setTabCompleter(new ExpTab());
			this.getCommand("xp").setTabCompleter(new XpTab());
		}
	}

	public FileConfiguration getConfig() {
		return cfg.getConfig();
	}

	public FileConfiguration getLang() {
		return msg.getConfig();
	}

	public String f(String msg) {
		if (getServer().getVersion().contains("1.16"))
			msg = HexUtil.hex(msg);
		return ChatColor.translateAlternateColorCodes('&', msg);
	}

	public boolean isNewerVersion() {
		 try {
	            Class<?> class_Material = Material.class;
	            Method method = class_Material.getDeclaredMethod("matchMaterial", String.class, Boolean.TYPE);
	            return (method != null);
	        } catch(ReflectiveOperationException ex) {
	        	return false;
	        }
	}


    private boolean setupBottles() {
    	craftBukkitVersion = "N/A";

        try {
            craftBukkitVersion = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        } catch (ArrayIndexOutOfBoundsException e) {
            return false;
        }

		try {
			Class<?> clazz = Class.forName(String.format(NMS_CLASS_NAME, craftBukkitVersion));
			bottle = (VersionData) clazz.getConstructor(this.getClass()).newInstance(this);
			return true;
		} catch (ReflectiveOperationException e) {
			getLogger().severe(e.getMessage());
			return false;
		}
    }

	public boolean hasVault() {
		return Bukkit.getServer().getPluginManager().getPlugin("Vault") != null;
	}

	private boolean hasUpdate() {
		UpdateChecker updater = new UpdateChecker(this, SPIGOT_PROJECT_ID);
        try {
            if (updater.checkForUpdates()) {
                getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "You are using an older version of XpBottles (" + updater.getCurrentVersion() + ")!");
                getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "Download the newest version (" + updater.getLatestVersion() + ") here:");
                getServer().getConsoleSender().sendMessage(ChatColor.DARK_AQUA + updater.getResourceURL());
                getServer().getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=");
                return true;
            } else {
                getServer().getConsoleSender().sendMessage("[XpBottles] Plugin is up to date! - " + updater.getCurrentVersion());
                return false;
            }
        } catch (Exception e) {
            getLogger().info("XpBottles Could not check for updates!");
            return false;
        }
	}

}
