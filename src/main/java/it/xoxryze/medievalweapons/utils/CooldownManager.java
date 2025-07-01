package it.xoxryze.medievalweapons.utils;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();

    public void setCooldown(Player player, String ability, double seconds) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid)) {
            cooldowns.put(uuid, new HashMap<>());
        }

        long cooldownTime = (long) (seconds * 1000);
        cooldowns.get(uuid).put(ability, System.currentTimeMillis() + cooldownTime);
    }

    public boolean hasCooldown(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid) || !cooldowns.get(uuid).containsKey(ability)) {
            return false;
        }

        return cooldowns.get(uuid).get(ability) > System.currentTimeMillis();
    }

    public long getRemainingCooldown(Player player, String ability) {
        UUID uuid = player.getUniqueId();
        if (!cooldowns.containsKey(uuid) || !cooldowns.get(uuid).containsKey(ability)) {
            return 0;
        }

        long remaining = cooldowns.get(uuid).get(ability) - System.currentTimeMillis();
        return remaining > 0 ? remaining : 0;
    }
}