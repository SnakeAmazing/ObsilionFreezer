package es.obsilion.freezer.player;

import java.util.UUID;

public class FrozenPlayer {

    private final UUID uuid;
    private final UUID operator;

    public FrozenPlayer(UUID uuid, UUID operator) {
        this.uuid = uuid;
        this.operator = operator;
    }

    public UUID getVictim() {
        return uuid;
    }

    public UUID getOperator() {
        return operator;
    }
}
