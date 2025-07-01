package it.xoxryze.medievalweapons.api.events;

import it.xoxryze.medievalweapons.models.MeleeWeapon;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public class CustomWeaponUseEvent extends PlayerEvent implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private final MeleeWeapon weapon;
    private final ItemStack item;
    private double damage;

    public CustomWeaponUseEvent(Player player, MeleeWeapon weapon, ItemStack item, double damage) {
        super(player);
        this.weapon = weapon;
        this.item = item;
        this.damage = damage;
    }

    public MeleeWeapon getWeapon() {
        return weapon;
    }

    public ItemStack getItem() {
        return item;
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