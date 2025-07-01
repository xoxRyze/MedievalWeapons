package it.xoxryze.medievalweapons.api;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.api.exceptions.WeaponNotFoundException;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.Bow;
import it.xoxryze.medievalweapons.models.MeleeWeapon;
import it.xoxryze.medievalweapons.models.Shield;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class MedievalWeaponsAPI {
    private static MedievalWeaponsAPI instance;
    private final MedievalWeapons plugin;
    private final ConfigManager configManager;

    private MedievalWeaponsAPI(MedievalWeapons plugin) {
        this.plugin = plugin;
        this.configManager = plugin.getConfigManager();
    }

    public static void init(MedievalWeapons plugin) {
        if (instance != null) {
            throw new IllegalStateException("API is already initialized");
        }
        instance = new MedievalWeaponsAPI(plugin);
    }

    public static MedievalWeaponsAPI getInstance() {
        if (instance == null) {
            throw new IllegalStateException("API has not been initialized yet");
        }
        return instance;
    }

    public Map<String, MeleeWeapon> getWeapons() {
        return configManager.getWeapons();
    }

    public Map<String, Shield> getShields() {
        return configManager.getShields();
    }

    public Map<String, Bow> getBows() {
        return configManager.getBows();
    }

    public MeleeWeapon getWeapon(String name) throws WeaponNotFoundException {
        MeleeWeapon weapon = configManager.getWeapons().get(name.toLowerCase());
        if (weapon == null) {
            throw new WeaponNotFoundException("Weapon '" + name + "' not found");
        }
        return weapon;
    }

    public Shield getShield(String name) throws WeaponNotFoundException {
        Shield shield = configManager.getShields().get(name.toLowerCase());
        if (shield == null) {
            throw new WeaponNotFoundException("Shield '" + name + "' not found");
        }
        return shield;
    }

    public Bow getBow(String name) throws WeaponNotFoundException {
        Bow bow = configManager.getBows().get(name.toLowerCase());
        if (bow == null) {
            throw new WeaponNotFoundException("Bow '" + name + "' not found");
        }
        return bow;
    }

    public ItemStack giveWeapon(Player player, String weaponName) throws WeaponNotFoundException {
        MeleeWeapon weapon = getWeapon(weaponName);
        ItemStack item = weapon.createItem();
        player.getInventory().addItem(item);
        return item;
    }

    public ItemStack giveShield(Player player, String shieldName) throws WeaponNotFoundException {
        Shield shield = getShield(shieldName);
        ItemStack item = shield.createItem();
        player.getInventory().addItem(item);
        return item;
    }

    public ItemStack giveBow(Player player, String bowName) throws WeaponNotFoundException {
        Bow bow = getBow(bowName);
        ItemStack item = bow.createItem();
        player.getInventory().addItem(item);
        return item;
    }

    public boolean isCustomWeapon(ItemStack item) {
        if (item == null) return false;

        for (MeleeWeapon weapon : configManager.getWeapons().values()) {
            if (matchesWeapon(item, weapon)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCustomShield(ItemStack item) {
        if (item == null || item.getType() != Material.SHIELD) return false;

        for (Shield shield : configManager.getShields().values()) {
            if (matchesShield(item, shield)) {
                return true;
            }
        }
        return false;
    }

    public boolean isCustomBow(ItemStack item) {
        if (item == null || item.getType() != Material.BOW) return false;

        for (Bow bow : configManager.getBows().values()) {
            if (matchesBow(item, bow)) {
                return true;
            }
        }
        return false;
    }

    private boolean matchesWeapon(ItemStack item, MeleeWeapon weapon) {
        if (item.getType() != weapon.getMaterial()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasCustomModelData() &&
                meta.getCustomModelData() == weapon.getCustomModelData();
    }

    private boolean matchesShield(ItemStack item, Shield shield) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasCustomModelData() &&
                meta.getCustomModelData() == shield.getCustomModelData();
    }

    private boolean matchesBow(ItemStack item, Bow bow) {
        ItemMeta meta = item.getItemMeta();
        return meta != null && meta.hasCustomModelData() &&
                meta.getCustomModelData() == bow.getCustomModelData();
    }
}