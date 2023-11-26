package es.obsilion.freezer.player;

import es.obsilion.freezer.ObsilionFreezer;
import es.obsilion.freezer.configuration.MainConfig;
import es.obsilion.freezer.message.MessageDecorator;
import org.bukkit.entity.Player;

import java.util.*;

public class SimpleFrozenPlayerHandler implements FrozenPlayerHandler {

    private final Map<UUID, FrozenPlayer> frozenPlayers = new HashMap<>();
    private final Map<UUID, Long> messageTimes = new HashMap<>();

    private final MainConfig config;

    public SimpleFrozenPlayerHandler(ObsilionFreezer plugin) {
        this.config = plugin.getConfiguration();
    }

    @Override
    public void add(Player player, Player operator) {
        FrozenPlayer frozenPlayer = new FrozenPlayer(player.getUniqueId(), operator.getUniqueId());
        frozenPlayers.put(player.getUniqueId(), frozenPlayer);
        messageTimes.put(player.getUniqueId(), System.currentTimeMillis());

        player.sendMessage(MessageDecorator.decorate(config.youHaveBeenFrozen()));
        player.sendMessage(MessageDecorator.decorate(config.chatOnlySeenByMods()));
        operator.sendMessage(MessageDecorator.decorate(config.youHaveFrozenPlayer()));
    }

    @Override
    public boolean contains(Player player) {
        return frozenPlayers.containsKey(player.getUniqueId());
    }

    @Override
    public void remove(Player player) {
        frozenPlayers.remove(player.getUniqueId());
        player.sendMessage(MessageDecorator.decorate(config.youHaveBeenUnFrozen()));
    }

    @Override
    public boolean check(Player player) {
        if (contains(player)) {
            long time = messageTimes.get(player.getUniqueId());

            if (time < System.currentTimeMillis()) {
                messageTimes.put(player.getUniqueId(), System.currentTimeMillis()
                        + (config.timeToWaitBeforeSendingMessage() * 1000L));
                player.sendMessage(MessageDecorator.decorate(config.cantDoThat()));
            }

            return true;
        }

        return false;
    }

    @Override
    public UUID getOperator(Player player) {
        return frozenPlayers.get(player.getUniqueId()).getOperator();
    }

    @Override
    public Collection<UUID> getVictims() {
        return frozenPlayers.keySet();
    }

    @Override
    public Collection<FrozenPlayer> getFrozenPlayers() {
        return frozenPlayers.values();
    }
}
