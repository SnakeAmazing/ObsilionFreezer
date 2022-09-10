package es.obsilion.freezer.player;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface FrozenPlayerHandler {

    void add(Player player, Player operator);

    boolean contains(Player player);

    void remove(Player player);

    boolean check(Player player);

    UUID getOperator(Player player);
}
