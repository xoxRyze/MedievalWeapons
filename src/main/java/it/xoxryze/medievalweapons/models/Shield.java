package it.xoxryze.medievalweapons.models;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Shield {
    private final String name;
    private final int customModelData;
    private final double cooldown;
    private final int maxUses;
    private final List<String> lore;
    private int remainingUses;

    public Shield(ConfigurationSection section) {
        this.name = section.getString("name", "Unnamed Shield");
        this.customModelData = section.getInt("custom-model-data", 0);
        this.cooldown = section.getDouble("cooldown", 1.0);
        this.maxUses = section.getInt("max-uses", -1);
        this.lore = section.getStringList("lore");
        this.remainingUses = maxUses;
    }

    public String getName() { return name; }
    public int getCustomModelData() { return customModelData; }
    public double getCooldown() { return cooldown; }
    public int getMaxUses() { return maxUses; }
    public List<String> getLore() { return lore; }
    public int getRemainingUses() { return remainingUses; }

    public void setRemainingUses(int uses) {
        this.remainingUses = uses;
    }

    public ItemStack createItem() {
        ItemStack item = new ItemStack(Material.SHIELD);
        return item;
    }
}