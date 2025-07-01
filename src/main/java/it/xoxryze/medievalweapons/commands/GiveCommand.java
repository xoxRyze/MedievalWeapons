package it.xoxryze.medievalweapons.commands;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.Bow;
import it.xoxryze.medievalweapons.models.MeleeWeapon;
import it.xoxryze.medievalweapons.models.Shield;
import it.xoxryze.medievalweapons.utils.ItemBuilder;
import it.xoxryze.medievalweapons.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 3) {
            sender.sendMessage("§cUtilizza /mw give <player> <weapon>");
            return;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            sender.sendMessage(MessageUtils.getMessage("player-not-found", args[1]));
            return;
        }

        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        String weaponName = args[2].toLowerCase();

        if (configManager.getWeapons().containsKey(weaponName)) {
            MeleeWeapon weapon = configManager.getWeapons().get(weaponName);
            ItemStack item = new ItemBuilder(weapon.getMaterial())
                    .setName(weapon.getName())
                    .setCustomModelData(weapon.getCustomModelData())
                    .setLore(weapon.getLore())
                    .addLoreLine("§8• §eᴜᴛɪʟɪᴢᴢɪ ʀɪᴍᴀɴᴇɴᴛɪ: §f" + weapon.getRemainingUses())
                    .build();

            target.getInventory().addItem(item);
            sender.sendMessage(MessageUtils.getMessage("weapon-given", weapon.getName(), target.getName()));
            target.sendMessage(MessageUtils.getMessage("weapon-received", weapon.getName()));
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

            target.getInventory().addItem(item);
            sender.sendMessage(MessageUtils.getMessage("shield-given", shield.getName(), target.getName()));
            target.sendMessage(MessageUtils.getMessage("shield-received", shield.getName()));
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

            target.getInventory().addItem(item);
            target.sendMessage(MessageUtils.getMessage("bow-received", bow.getName()));
            return;
        }

        sender.sendMessage(MessageUtils.getMessage("weapon-not-found", weaponName));
    }
}