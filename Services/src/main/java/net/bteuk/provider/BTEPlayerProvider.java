package net.bteuk.provider;

import net.bteuk.api.BTEPlayer;

import java.util.List;
import java.util.Optional;

public interface BTEPlayerProvider {

    Optional<BTEPlayer> getPlayer(String uuid);

    List<BTEPlayer> getPlayers();

    boolean hasPlayer(BTEPlayer player);

    String getVersion();

    static String getName() {
        return "BTEPlayerProvider";
    }
}
