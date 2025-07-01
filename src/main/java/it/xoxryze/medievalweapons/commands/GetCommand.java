package it.xoxryze.medievalweapons.commands;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.Bow;
import it.xoxryze.medievalweapons.models.MeleeWeapon;
import it.xoxryze.medievalweapons.models.Shield;
import it.xoxryze.medievalweapons.utils.ItemBuilder;
import it.xoxryze.medievalweapons.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GetCommand {
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("§cUtilizza /mw get <weapon>");
            return;
        }

        Player player = (Player) sender;
        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        String weaponName = args[1].toLowerCase();

        if (configManager.getWeapons().containsKey(weaponName)) {
            MeleeWeapon weapon = configManager.getWeapons().get(weaponName);
            ItemStack item = new ItemBuilder(weapon.getMaterial())
                    .setName(weapon.getName())
                    .setCustomModelData(weapon.getCustomModelData())
                    .setLore(weapon.getLore())
                    .addLoreLine("§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f" + weapon.getRemainingUses())
                    .build();

            player.getInventory().addItem(item);
            player.sendMessage(MessageUtils.getMessage("weapon-received", weapon.getName()));
            return;
        }

        if (configManager.getShields().containsKey(weaponName)) {
            Shield shield = configManager.getShields().get(weaponName);
            ItemStack item = new ItemBuilder(Material.SHIELD)
                    .setName(shield.getName())
                    .setCustomModelData(shield.getCustomModelData())
                    .setLore(shield.getLore())
                    .addLoreLine("§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f" + shield.getRemainingUses())
                    .build();

            player.getInventory().addItem(item);
            player.sendMessage(MessageUtils.getMessage("shield-received", shield.getName()));
            return;
        }
        if (configManager.getBows().containsKey(weaponName)) {
            Bow bow = configManager.getBows().get(weaponName);
            ItemStack item = new ItemBuilder(Material.BOW)
                    .setName(bow.getName())
                    .setCustomModelData(bow.getCustomModelData())
                    .setLore(bow.getLore())
                    .addLoreLine("§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f" + bow.getRemainingUses())
                    .build();

            player.getInventory().addItem(item);
            player.sendMessage(MessageUtils.getMessage("bow-received", bow.getName()));
            return;
        }

        player.sendMessage(MessageUtils.getMessage("weapon-not-found", weaponName));
    }
}