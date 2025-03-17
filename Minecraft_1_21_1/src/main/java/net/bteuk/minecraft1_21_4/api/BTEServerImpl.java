package net.bteuk.minecraft1_21_4.api;

import net.bteuk.api.BTEServer;
import org.bukkit.Server;

public record BTEServerImpl(Server server) implements BTEServer {

    @Override
    public String getName() {
        return server.getName();
    }
}
