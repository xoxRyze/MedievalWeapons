package it.xoxryze.medievalweapons;

import it.xoxryze.medievalweapons.api.MedievalWeaponsAPI;
import it.xoxryze.medievalweapons.commands.*;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.listeners.BowUseListener;
import it.xoxryze.medievalweapons.listeners.ItemUseListener;
import it.xoxryze.medievalweapons.listeners.ShieldUseListener;
import it.xoxryze.medievalweapons.utils.CooldownManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MedievalWeapons extends JavaPlugin {
    private static MedievalWeapons instance;
    private ConfigManager configManager;
    private CooldownManager cooldownManager;

    @Override
    public void onEnable() {
        instance = this;

        this.configManager = new ConfigManager(this);
        this.cooldownManager = new CooldownManager();
        MedievalWeaponsAPI.init(this);
        configManager.loadConfigs();

        getCommand("medievalweapons").setExecutor(new CommandManager());
        getCommand("medievalweapons").setTabCompleter(new MWTabComplete(this));
        getServer().getPluginManager().registerEvents(new ItemUseListener(), this);
        getServer().getPluginManager().registerEvents(new ShieldUseListener(), this);
        getServer().getPluginManager().registerEvents(new BowUseListener(), this);
        getLogger().info(" ");
        getLogger().info("========================================================");
        getLogger().info("MedievalWeapons by @RyzeProjects abilitato con successo!");
        getLogger().info("Autore: RyZeeTheBest");
        getLogger().info("Versione: 1.0");
        getLogger().info("For @TegeaRoleplay");
        getLogger().info("========================================================");
        getLogger().info(" ");
    }

    @Override
    public void onDisable() {
        getLogger().info(" ");
        getLogger().info("==============================================");
        getLogger().info("MedievalWeapons by @RyzeProjects disabilitato!");
        getLogger().info("==============================================");
        getLogger().info(" ");
    }

    public static MedievalWeapons getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CooldownManager getCooldownManager() {
        return cooldownManager;
    }
}