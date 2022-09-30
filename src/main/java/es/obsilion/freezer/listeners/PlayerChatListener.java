package es.obsilion.freezer.listeners;

import es.obsilion.freezer.ObsilionFreezer;
import es.obsilion.freezer.player.FrozenPlayer;
import es.obsilion.freezer.player.FrozenPlayerHandler;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.UUID;

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

            event.message(event.message().hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                    Component.text("Este mensaje solo puede verlo el moderador, no te preocupes."))));

            return;
        }

        for (FrozenPlayer frozenPlayer : frozenHandler.getFrozenPlayers()) {
            if (frozenPlayer.getOperator() == event.getPlayer().getUniqueId()) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (frozenPlayer.getOperator() == player.getUniqueId() || frozenPlayer.getVictim() == player.getUniqueId()) {
                        continue;
                    }
                    event.viewers().remove(player);
                }

                return;
            }
        }

        for (UUID uuid : frozenHandler.getVictims()) {
            event.viewers().remove(Bukkit.getPlayer(uuid));
        }
    }
}
