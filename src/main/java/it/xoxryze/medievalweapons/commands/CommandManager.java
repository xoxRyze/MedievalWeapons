package it.xoxryze.medievalweapons.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandManager implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "get":
                new GetCommand().execute(sender, args);
                break;
            case "give":
                new GiveCommand().execute(sender, args);
                break;
            case "reload":
                new ReloadCommand().execute(sender, args);
                break;
            case "list":
                new ListCommand().execute(sender, args);
                break;
            default:
                sendHelp(sender);
                break;
        }
        return true;
    }

    private void sendHelp(CommandSender sender) {
        sender.sendMessage(" ");
        sender.sendMessage("§x§F§6§9§F§1§8§lᴍ§x§F§4§9§6§1§8§lᴇ§x§F§2§8§E§1§8§lᴅ§x§F§0§8§5§1§8§lɪ§x§E§E§7§D§1§8§lᴇ§x§E§B§7§4§1§8§lᴠ§x§E§9§6§B§1§8§lᴀ§x§E§7§6§3§1§8§lʟ §x§E§7§6§4§1§8§lᴡ§x§E§A§6§E§1§8§lᴇ§x§E§C§7§8§1§8§lᴀ§x§E§F§8§1§1§8§lᴘ§x§F§1§8§B§1§8§lᴏ§x§F§4§9§5§1§8§lɴ§x§F§6§9§F§1§8§lѕ");
        sender.sendMessage("§e/ᴍᴡ ɢᴇᴛ <ᴏɢɢᴇᴛᴛᴏ> §7• §fOttieni un arma o un oggetto");
        sender.sendMessage("§e/ᴍᴡ ɢɪᴠᴇ <ᴘʟᴀʏᴇʀ> <ᴏɢɢᴇᴛᴛᴏ> §7• §fDai un arma o un oggetto a qualcuno");
        sender.sendMessage("§e/ᴍᴡ ʀᴇʟᴏᴀᴅ <ᴛʏᴘᴇ> §7• §fRicarica un file del plugin");
        sender.sendMessage("§e/ᴍᴡ ʟɪѕᴛ <ᴘᴀɢᴇ/ᴀʟʟ> §7• §fLista delle armi e degli oggetti");
        sender.sendMessage(" ");
    }
}