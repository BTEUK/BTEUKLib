package net.bteuk.services.serviceloader;

import net.bteuk.services.api.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class PlayerLoader {

    public static List<Player> findPlayers() {
        List<Player> players = new ArrayList<>();
        ServiceLoader<Player> loader = ServiceLoader.load(Player.class);
        for (Player player : loader) {
            players.add(player);
        }
        return players;
    }
}
