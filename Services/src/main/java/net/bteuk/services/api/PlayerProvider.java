package net.bteuk.services.api;

import java.util.List;
import java.util.Optional;

public interface PlayerProvider {

    void addPlayer(Player player);

    void removePlayer(Player player);

    Optional<Player> getPlayer(String uuid);

    List<Player> getPlayers();

    boolean hasPlayer();

}
