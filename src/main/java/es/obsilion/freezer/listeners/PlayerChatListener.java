package es.obsilion.freezer.listeners;

import es.obsilion.freezer.ObsilionFreezer;
import es.obsilion.freezer.player.FrozenPlayerHandler;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatListener implements Listener {

    private final FrozenPlayerHandler frozenHandler;

    public PlayerChatListener(ObsilionFreezer plugin) {
        this.frozenHandler = plugin.getFrozenHandler();
    }

    @EventHandler
    public void onPlayerChatEvent(AsyncChatEvent event) {
        if (frozenHandler.contains(event.getPlayer())) {
            event.viewers().clear();
            event.viewers().add(Bukkit.getPlayer(frozenHandler.getOperator(event.getPlayer())));
            event.viewers().add(event.getPlayer());
        }
    }
}
