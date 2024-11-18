package net.bteuk.services.serviceloader;

import net.bteuk.services.api.PlayerProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PlayerLoader {

    public static List<PlayerProvider> findPlayerProviders() {
        List<PlayerProvider> playerProviders = new ArrayList<>();
        ServiceLoader<PlayerProvider> loader = ServiceLoader.load(PlayerProvider.class);
        for (PlayerProvider playerProvider : loader) {
            playerProviders.add(playerProvider);
        }
        return playerProviders;
    }
}
