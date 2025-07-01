package it.xoxryze.medievalweapons.utils;

import it.xoxryze.medievalweapons.MedievalWeapons;
import org.bukkit.configuration.file.FileConfiguration;

public class MessageUtils {
    public static String getMessage(String key, String... replacements) {
        FileConfiguration messages = MedievalWeapons.getInstance().getConfigManager().getMessagesConfig();
        String message = messages.getString(key, "&cMessaggio non trovato: " + key);

        for (int i = 0; i < replacements.length; i++) {
            message = message.replace("{" + i + "}", replacements[i]);
        }

        return message.replace('&', '§');
    }
}