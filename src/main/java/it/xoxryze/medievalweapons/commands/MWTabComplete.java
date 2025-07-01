package it.xoxryze.medievalweapons.commands;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class MWTabComplete implements TabCompleter {
    private static final List<String> MAIN_COMMANDS = Arrays.asList("get", "give", "reload", "list", "help");
    private static final List<String> RELOAD_TYPES = Arrays.asList("weapons", "config", "messages", "all");

    private final MedievalWeapons plugin;

    public MWTabComplete(MedievalWeapons plugin) {
        this.plugin = plugin;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        final List<String> completions = new ArrayList<>();

        if (args.length == 1) {
            return StringUtil.copyPartialMatches(args[0], MAIN_COMMANDS, new ArrayList<>());
        }

        switch (args[0].toLowerCase()) {
            case "get":
                return handleGetTabComplete(sender, args);
            case "give":
                return handleGiveTabComplete(sender, args);
            case "reload":
                return handleReloadTabComplete(sender, args);
            case "list":
                return handleListTabComplete(sender, args);
            default:
                return Collections.emptyList();
        }
    }

    private List<String> handleGetTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            ConfigManager config = plugin.getConfigManager();
            List<String> allItems = new ArrayList<>();
            allItems.addAll(config.getWeapons().keySet());
            allItems.addAll(config.getShields().keySet());
            allItems.addAll(config.getBows().keySet());

            return StringUtil.copyPartialMatches(args[1], allItems, new ArrayList<>());
        }
        return Collections.emptyList();
    }

    private List<String> handleGiveTabComplete(CommandSender sender, String[] args) {
        ConfigManager config = plugin.getConfigManager();

        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1],
                    plugin.getServer().getOnlinePlayers().stream()
                            .map(Player::getName)
                            .collect(Collectors.toList()),
                    new ArrayList<>());
        } else if (args.length == 3) {
            List<String> allItems = new ArrayList<>();
            allItems.addAll(config.getWeapons().keySet());
            allItems.addAll(config.getShields().keySet());
            allItems.addAll(config.getBows().keySet());

            return StringUtil.copyPartialMatches(args[2], allItems, new ArrayList<>());
        }
        return Collections.emptyList();
    }

    private List<String> handleReloadTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            return StringUtil.copyPartialMatches(args[1], RELOAD_TYPES, new ArrayList<>());
        }
        return Collections.emptyList();
    }

    private List<String> handleListTabComplete(CommandSender sender, String[] args) {
        if (args.length == 2) {
            ConfigManager config = plugin.getConfigManager();
            int totalItems = config.getWeapons().size() + config.getShields().size() + config.getBows().size();
            int totalPages = (int) Math.ceil((double) totalItems / 10);

            List<String> suggestions = new ArrayList<>();
            suggestions.add("all");

            // Add page numbers
            for (int i = 1; i <= totalPages; i++) {
                suggestions.add(String.valueOf(i));
            }

            return StringUtil.copyPartialMatches(args[1], suggestions, new ArrayList<>());
        }
        return Collections.emptyList();
    }
}