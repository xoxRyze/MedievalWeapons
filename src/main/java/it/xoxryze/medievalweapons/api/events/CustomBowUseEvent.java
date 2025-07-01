package it.xoxryze.medievalweapons.api.events;

import it.xoxryze.medievalweapons.models.Bow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class CustomBowUseEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final Bow bow;
    private final ItemStack item;
    private final Arrow arrow;
    private double damage;

    public CustomBowUseEvent(Player player, Bow bow, ItemStack item, Arrow arrow, double damage) {
        super(player);
        this.bow = bow;
        this.item = item;
        this.arrow = arrow;
        this.damage = damage;
    }

    public Bow getBow() {
        return bow;
    }

    public ItemStack getItem() {
        return item;
    }

    public Arrow getArrow() {
        return arrow;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
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