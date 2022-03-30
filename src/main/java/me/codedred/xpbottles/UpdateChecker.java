package me.codedred.xpbottles;

import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class UpdateChecker {

	private static final String SPIGOT_CHECK_URL = "https://api.spigotmc.org/legacy/update.php?resource=%s";
	private static final String SPIGOT_RESOURCE_URL = "https://www.spigotmc.org/resources/%s";

	private final JavaPlugin plugin;
	private final int projectId;
	private String newVersion;
	private URL checkURL;

	public UpdateChecker(JavaPlugin plugin, int projectID) {
		this.plugin = plugin;
		this.projectId = projectID;
		this.newVersion = plugin.getDescription().getVersion();
		try {
			this.checkURL = new URL(String.format(SPIGOT_CHECK_URL, projectID));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public int getProjectID() {
		return projectId;
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

	public String getLatestVersion() {
		return newVersion;
	}

	public String getCurrentVersion() {
		return plugin.getDescription().getVersion();
	}

	public String getResourceURL() {
		return String.format(SPIGOT_RESOURCE_URL, projectId);
	}

	public boolean checkForUpdates() throws Exception {
		URLConnection con = checkURL.openConnection();
		this.newVersion = new BufferedReader(new InputStreamReader(con.getInputStream())).readLine();
		return !getCurrentVersion().equals(newVersion);
	}

}
