package net.bteuk.minecraft1_21_4.api;

import net.bteuk.api.BTEInventory;
import net.bteuk.api.BTEPlayer;
import net.bteuk.api.BTEServer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.UUID;

public record BTEPlayerImpl(Player player) implements BTEPlayer {

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public UUID getUuid() {
        return player.getUniqueId();
    }

    @Override
    public BTEServer getServer() {
        return new BTEServerImpl(player.getServer());
    }

    @Override
    public boolean hasPermission(String permissionNode) {
        return player.hasPermission(permissionNode);
    }

    @Override
    public void openInventory(BTEInventory<?> bteInventory) {
        if (bteInventory.inventory() instanceof Inventory inventory) {
            player.openInventory(inventory);
        }
    }
}
