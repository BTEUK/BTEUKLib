package net.bteuk.minecraft1_21_4.provider;

import net.bteuk.api.BTEPlayer;
import net.bteuk.minecraft1_21_4.api.BTEPlayerImpl;
import net.bteuk.provider.BTEPlayerProvider;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static net.bteuk.minecraft1_21_4.Version.VERSION;

public class BTEPlayerProviderImpl implements BTEPlayerProvider {

    @Override
    public Optional<BTEPlayer> getPlayer(String uuid) {
        return getPlayers().stream().filter(player -> player.getUuid().toString().equals(uuid)).findFirst();
    }

    @Override
    public List<BTEPlayer> getPlayers() {
        List<BTEPlayer> btePlayers = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(player -> btePlayers.add(new BTEPlayerImpl(player)));
        return btePlayers;
    }

    @Override
    public boolean hasPlayer(BTEPlayer player) {
        return getPlayers().contains(player);
    }

    @Override
    public String getVersion() {
        return VERSION;
    }
}
