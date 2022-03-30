package me.codedred.xpbottles.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HexUtil {

    private static final Pattern HEX_PATTERN = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String hex(String msg) {
        Matcher match = HEX_PATTERN.matcher(msg);
        while (match.find()) {
            String color = msg.substring(match.start(), match.end());
            msg = msg.replace(color, ChatColor.of(color) + "");
            match = HEX_PATTERN.matcher(msg);
        }
        return msg;
    }

}
