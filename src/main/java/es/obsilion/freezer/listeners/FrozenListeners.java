package es.obsilion.freezer.listeners;

import es.obsilion.freezer.ObsilionFreezer;
import es.obsilion.freezer.player.FrozenPlayerHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class FrozenListeners implements Listener {

    private final FrozenPlayerHandler frozenHandler;

    public FrozenListeners(ObsilionFreezer plugin) {
        this.frozenHandler = plugin.getFrozenHandler();
    }

    @EventHandler
    public void onPlayerCommandEvent(PlayerCommandPreprocessEvent event) {
        if (frozenHandler.check(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (frozenHandler.check(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerMoveEvent(PlayerMoveEvent event) {
        if (frozenHandler.check(event.getPlayer())) {
            event.getPlayer().teleport(event.getFrom());
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        if (frozenHandler.check(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onOpenInventoryEvent(InventoryOpenEvent event) {
        if (!(event.getPlayer() instanceof Player player)) return;

        if (frozenHandler.check(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onInventoryInteractEvent(InventoryInteractEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (frozenHandler.check(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        if (frozenHandler.contains(player)) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (frozenHandler.check(event.getPlayer())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        if (frozenHandler.check(event.getPlayer())) {
            event.setCancelled(true);
        }
    }
}
