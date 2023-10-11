package me.codedred.xpbottles.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;

import java.lang.reflect.Method;

public class ServerUtil {

    private static Boolean isNewerVersion = null;

    private ServerUtil() {
        throw new IllegalStateException("Utility Class");
    }

    public static boolean isNewerVersion() {
        if (isNewerVersion == null) {
            try {
                Class<?> classMaterial = Material.class;
                Method method = classMaterial.getDeclaredMethod("matchMaterial", String.class, boolean.class);
                isNewerVersion = (method != null);
            } catch (ReflectiveOperationException ex) {
                isNewerVersion = false;
            }
        }
        return isNewerVersion;
    }

    /**
     * Checks if the server version is 1.16+
     *
     * @return true if so
     */
    public static boolean isRisenVersion() {
        String[] versionParts = Bukkit.getServer().getVersion().split("\\.");

        int majorVersion = Integer.parseInt(versionParts[1].replaceAll("[^0-9]", ""));
        int minorVersion = 0;
        if (versionParts.length >= 3)
            minorVersion = Integer.parseInt(versionParts[2].replaceAll("[^0-9]", ""));

        return majorVersion >= 16 && minorVersion >= 0;
    }
}
