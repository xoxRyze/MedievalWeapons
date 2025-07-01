package it.xoxryze.medievalweapons.commands;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.models.Bow;
import it.xoxryze.medievalweapons.models.MeleeWeapon;
import it.xoxryze.medievalweapons.models.Shield;
import it.xoxryze.medievalweapons.utils.MessageUtils;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListCommand {
    private static final int ITEMS_PER_PAGE = 10;

    public void execute(CommandSender sender, String[] args) {
        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        Map<String, MeleeWeapon> weapons = configManager.getWeapons();
        Map<String, Shield> shields = configManager.getShields();
        Map<String, Bow> bows = configManager.getBows();

        List<String> allItems = new ArrayList<>();
        allItems.addAll(weapons.keySet());
        allItems.addAll(shields.keySet());
        allItems.addAll(bows.keySet());

        if (allItems.isEmpty()) {
            sender.sendMessage(MessageUtils.getMessage("no-weapons-found"));
            return;
        }

        if (args.length < 2 || args[1].equalsIgnoreCase("1")) {
            sendPage(sender, allItems, 1);
            return;
        }

        if (args[1].equalsIgnoreCase("all")) {
            sendAllItems(sender, allItems);
            return;
        }

        try {
            int page = Integer.parseInt(args[1]);
            sendPage(sender, allItems, page);
        } catch (NumberFormatException e) {
            sender.sendMessage(MessageUtils.getMessage("invalid-page-number"));
        }
    }

    private void sendPage(CommandSender sender, List<String> items, int page) {
        int totalPages = (int) Math.ceil((double) items.size() / ITEMS_PER_PAGE);
        if (page < 1 || page > totalPages) {
            sender.sendMessage(MessageUtils.getMessage("invalid-page-number"));
            return;
        }

        sender.sendMessage(MessageUtils.getMessage("weapon-list-header", String.valueOf(page), String.valueOf(totalPages)));

        int start = (page - 1) * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, items.size());

        for (int i = start; i < end; i++) {
            String itemName = items.get(i);
            TextComponent component = new TextComponent("§7• §f" + itemName);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/medievalweapons get " + itemName));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("§7Clicca per ottenere: " + itemName).create()));
            sender.spigot().sendMessage(component);
        }

        if (page < totalPages) {
            sender.sendMessage(MessageUtils.getMessage("next-page-hint", String.valueOf(page + 1)));
        }
    }

    private void sendAllItems(CommandSender sender, List<String> items) {
        sender.sendMessage(MessageUtils.getMessage("all-weapons-list"));

        for (String itemName : items) {
            TextComponent component = new TextComponent("§7• §f" + itemName);
            component.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/medievalweapons get " + itemName));
            component.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                    new ComponentBuilder("§7Clicca per ottenere: " + itemName).create()));
            sender.spigot().sendMessage(component);
        }
    }
}