package it.xoxryze.medievalweapons.config;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.models.Bow;
import it.xoxryze.medievalweapons.models.MeleeWeapon;
import it.xoxryze.medievalweapons.models.Shield;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ConfigManager {
    private final MedievalWeapons plugin;
    private FileConfiguration weaponsConfig;
    private FileConfiguration messagesConfig;
    private FileConfiguration pluginConfig;

    private final Map<String, Bow> bows = new HashMap<>();
    private final Map<String, MeleeWeapon> weapons = new HashMap<>();
    private final Map<String, Shield> shields = new HashMap<>();

    public ConfigManager(MedievalWeapons plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        loadWeaponsConfig();
        loadMessagesConfig();
        loadPluginConfig();
    }

    private void loadWeaponsConfig() {
        File weaponsFile = new File(plugin.getDataFolder(), "weapons.yml");
        if (!weaponsFile.exists()) {
            plugin.saveResource("weapons.yml", false);
        }
        weaponsConfig = YamlConfiguration.loadConfiguration(weaponsFile);

        ConfigurationSection weaponsSection = weaponsConfig.getConfigurationSection("weapons");
        if (weaponsSection != null) {
            for (String key : weaponsSection.getKeys(false)) {
                weapons.put(key, new MeleeWeapon(weaponsSection.getConfigurationSection(key)));
            }
        }
        ConfigurationSection shieldsSection = weaponsConfig.getConfigurationSection("shields");
        if (shieldsSection != null) {
            for (String key : shieldsSection.getKeys(false)) {
                shields.put(key, new Shield(shieldsSection.getConfigurationSection(key)));
            }
        }
        ConfigurationSection bowsSection = weaponsConfig.getConfigurationSection("bows");
        if (bowsSection != null) {
            for (String key : bowsSection.getKeys(false)) {
                bows.put(key, new Bow(bowsSection.getConfigurationSection(key)));
            }
        }
    }

    private void loadMessagesConfig() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
    }

    private void loadPluginConfig() {
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            plugin.saveResource("config.yml", false);
        }
        pluginConfig = YamlConfiguration.loadConfiguration(configFile);
    }

    public void reloadWeaponsConfig() {
        loadWeaponsConfig();
    }

    public void reloadMessagesConfig() {
        loadMessagesConfig();
    }

    public void reloadPluginConfig() {
        loadPluginConfig();
    }

    public Map<String, MeleeWeapon> getWeapons() {
        return weapons;
    }

    public Map<String, Shield> getShields() {
        return shields;
    }

    public FileConfiguration getWeaponsConfig() {
        return weaponsConfig;
    }

    public FileConfiguration getMessagesConfig() {
        return messagesConfig;
    }

    public FileConfiguration getPluginConfig() {
        return pluginConfig;
    }

    public Map<String, Bow> getBows() {
        return bows;
    }
}