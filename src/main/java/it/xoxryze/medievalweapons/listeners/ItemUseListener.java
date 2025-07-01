package it.xoxryze.medievalweapons.listeners;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.MeleeWeapon;
import it.xoxryze.medievalweapons.utils.CooldownManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemUseListener implements Listener {
    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack item = player.getInventory().getItemInMainHand();

        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        CooldownManager cooldownManager = MedievalWeapons.getInstance().getCooldownManager();

        for (MeleeWeapon weapon : configManager.getWeapons().values()) {
            if (isCustomWeapon(item, weapon)) {
                if (cooldownManager.hasCooldown(player, weapon.getName())) {
                    event.setCancelled(true);
                    player.sendActionBar("§x§F§F§3§3§0§0Devi aspettare prima di poterlo riutilizzare!");
                    return;
                }

                cooldownManager.setCooldown(player, weapon.getName(), weapon.getCooldown());

                event.setDamage(weapon.getDamage());

                if (weapon.getMaxUses() > 0) {
                    updateItemUses(player, item, weapon);
                }
                break;
            }
        }
    }

    private boolean isCustomWeapon(ItemStack item, MeleeWeapon weapon) {
        if (item == null || item.getType() != weapon.getMaterial()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelData()) {
            return false;
        }

        return meta.getCustomModelData() == weapon.getCustomModelData();
    }

    private void updateItemUses(Player player, ItemStack item, MeleeWeapon weapon) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        int remainingUses = weapon.getRemainingUses() - 1;
        weapon.setRemainingUses(remainingUses);

        List<String> lore = meta.getLore();
        if (lore != null && !lore.isEmpty()) {
            String lastLine = lore.get(lore.size() - 1);
            if (lastLine.startsWith("§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f")) {
                lore.set(lore.size() - 1, "§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f" + remainingUses);
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
        }

        if (remainingUses <= 0) {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage("§x§F§F§3§3§0§0La tua '" + weapon.getName() + "' si è rotta!");
        }
    }
}