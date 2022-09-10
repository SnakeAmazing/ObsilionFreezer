package es.obsilion.freezer.message;

import org.bukkit.ChatColor;

public final class MessageDecorator {

    public static String decorate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
