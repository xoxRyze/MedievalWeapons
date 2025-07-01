package it.xoxryze.medievalweapons.listeners;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.Shield;
import it.xoxryze.medievalweapons.utils.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ShieldUseListener implements Listener {
    @EventHandler
    public void onShieldUse(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();

        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        CooldownManager cooldownManager = MedievalWeapons.getInstance().getCooldownManager();

        for (Shield shield : configManager.getShields().values()) {
            if (isCustomShield(item, shield)) {
                if (cooldownManager.hasCooldown(player, shield.getName())) {
                    event.setCancelled(true);
                    player.sendActionBar("§x§F§F§3§3§0§0Devi aspettare prima di poterlo riutilizzare!");
                    return;
                }

                cooldownManager.setCooldown(player, shield.getName(), shield.getCooldown());

                if (shield.getMaxUses() > 0) {
                    updateShieldUses(player, item, shield);
                }
                break;
            }
        }
    }

    private boolean isCustomShield(ItemStack item, Shield shield) {
        if (item == null || item.getType() != Material.SHIELD) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasCustomModelData()) {
            return false;
        }

        return meta.getCustomModelData() == shield.getCustomModelData();
    }

    private void updateShieldUses(Player player, ItemStack item, Shield shield) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        int remainingUses = shield.getRemainingUses() - 1;
        shield.setRemainingUses(remainingUses);

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
            player.sendMessage("§x§F§F§3§3§0§0Il tuo scudo '" + shield.getName() + "' si è rotto!");
        }
    }
}