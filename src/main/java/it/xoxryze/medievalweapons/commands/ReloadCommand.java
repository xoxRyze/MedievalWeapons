package it.xoxryze.medievalweapons.commands;

import it.xoxryze.medievalweapons.MedievalWeapons;
import it.xoxryze.medievalweapons.config.ConfigManager;
import it.xoxryze.medievalweapons.utils.MessageUtils;
import org.bukkit.command.CommandSender;

public class ReloadCommand {
    public void execute(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("§cUtilizza /mw reload <type>");
            return;
        }

        ConfigManager configManager = MedievalWeapons.getInstance().getConfigManager();
        String type = args[1].toLowerCase();

        switch (type) {
            case "weapons":
                configManager.reloadWeaponsConfig();
                sender.sendMessage(MessageUtils.getMessage("config-reloaded", "weapons"));
                break;
            case "config":
                configManager.reloadPluginConfig();
                sender.sendMessage(MessageUtils.getMessage("config-reloaded", "config"));
                break;
            case "messages":
                configManager.reloadMessagesConfig();
                sender.sendMessage(MessageUtils.getMessage("config-reloaded", "messages"));
                break;
            case "all":
                configManager.loadConfigs();
                sender.sendMessage(MessageUtils.getMessage("config-reloaded", "all configurations"));
                break;
            default:
                sender.sendMessage("§cUtilizza /mw reload <type>");
                break;
        }
    }
}