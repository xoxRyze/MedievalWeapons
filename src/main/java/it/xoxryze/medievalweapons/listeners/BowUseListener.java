package it.xoxryze.medievalweapons.listeners;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.Bow;
import it.xoxryze.medievalweapons.utils.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class BowUseListener implements Listener {
    @EventHandler
    public void onBowShoot(EntityShootBowEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        ItemStack bow = event.getBow();

        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        CooldownManager cooldownManager = MedievalWeapons.getInstance().getCooldownManager();

        for (Bow customBow : configManager.getBows().values()) {
            if (isCustomBow(bow, customBow)) {
                if (cooldownManager.hasCooldown(player, customBow.getName())) {
                    event.setCancelled(true);
                    player.sendActionBar("§x§F§F§3§3§0§0Devi aspettare prima di poterlo riutilizzare!");
                    return;
                }

                cooldownManager.setCooldown(player, customBow.getName(), customBow.getCooldown());

                if (event.getProjectile() instanceof Arrow) {
                    Arrow arrow = (Arrow) event.getProjectile();
                    arrow.setDamage(customBow.getArrowDamage());
                    arrow.setCustomName(customBow.getName() + " Arrow");
                }

                if (customBow.getMaxUses() > 0) {
                    updateBowUses(player, bow, customBow);
                }
                break;
            }
        }
    }

    private boolean isCustomBow(ItemStack bow, Bow customBow) {
        if (bow == null || bow.getType() != Material.BOW) {
            return false;
        }

        ItemMeta meta = bow.getItemMeta();
        if (meta == null || !meta.hasCustomModelData()) {
            return false;
        }

        return meta.getCustomModelData() == customBow.getCustomModelData();
    }

    private void updateBowUses(Player player, ItemStack bow, Bow customBow) {
        ItemMeta meta = bow.getItemMeta();
        if (meta == null) return;

        int remainingUses = customBow.getRemainingUses() - 1;
        customBow.setRemainingUses(remainingUses);

        List<String> lore = meta.getLore();
        if (lore != null && !lore.isEmpty()) {
            String lastLine = lore.get(lore.size() - 1);
            if (lastLine.startsWith("§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f")) {
                lore.set(lore.size() - 1, "§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f" + remainingUses);
                meta.setLore(lore);
                bow.setItemMeta(meta);
            }
        }

        if (remainingUses <= 0) {
            player.getInventory().setItemInMainHand(null);
            player.sendMessage("§x§F§F§3§3§0§0Il tuo arco '" + customBow.getName() + "' si è rotto!");
        }
    }
}