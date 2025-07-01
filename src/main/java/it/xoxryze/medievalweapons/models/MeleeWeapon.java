package it.xoxryze.medievalweapons.models;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MeleeWeapon {
    private final String name;
    private final Material material;
    private final int customModelData;
    private final double damage;
    private final double cooldown;
    private final int maxUses;
    private final List<String> lore;
    private int remainingUses;

    public MeleeWeapon(ConfigurationSection section) {
        this.name = section.getString("name", "Unnamed Weapon");
        this.material = Material.valueOf(section.getString("material", "IRON_SWORD"));
        this.customModelData = section.getInt("custom-model-data", 0);
        this.damage = section.getDouble("damage", 5.0);
        this.cooldown = section.getDouble("cooldown", 1.0);
        this.maxUses = section.getInt("max-uses", -1);
        this.lore = section.getStringList("lore");
        this.remainingUses = maxUses;
    }

    public String getName() { return name; }
    public Material getMaterial() { return material; }
    public int getCustomModelData() { return customModelData; }
    public double getDamage() { return damage; }
    public double getCooldown() { return cooldown; }
    public int getMaxUses() { return maxUses; }
    public List<String> getLore() { return lore; }
    public int getRemainingUses() { return remainingUses; }

    public void setRemainingUses(int uses) {
        this.remainingUses = uses;
    }

    public ItemStack createItem() {
        ItemStack item = new ItemStack(material);
        return item;
    }
}