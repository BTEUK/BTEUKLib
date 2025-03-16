package net.bteuk.api;

import net.bteuk.services.api.Player;
import net.bteuk.services.api.PlayerProvider;
import net.bteuk.services.serviceloader.PlayerLoader;

import java.util.Collections;
import java.util.List;

public final class BTEUKAPI {

    private BTEUKAPI() {
        // Private constructor
    }

    public static List<Player> getOnlinePlayers() {
        List<PlayerProvider> provider = PlayerLoader.findPlayerProviders();
        if (provider.isEmpty()) {
            return Collections.emptyList();
        }
        return provider.getFirst().getPlayers();
    }
}
