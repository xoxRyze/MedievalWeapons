package it.xoxryze.medievalweapons.api.events;

import it.xoxryze.medievalweapons.models.Shield;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class CustomShieldUseEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Shield shield;
    private final ItemStack item;

    public CustomShieldUseEvent(Player player, Shield shield, ItemStack item) {
        super(player);
        this.shield = shield;
        this.item = item;
    }

    public Shield getShield() {
        return shield;
    }

    public ItemStack getItem() {
        return item;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}