package net.bteuk.services.api;

import java.util.UUID;

public interface Player {

    /**
     * Get the Name of the player.
     * @return String name
     */
    String getName();

    /**
     * Get the {@link UUID} of the player.
     * @return UUID
     */
    UUID getUuid();

    /**
     * Get the {@link Server} the player is connected to.
     * @return the server
     */
    Server getServer();

    /**
     * Check if the player has a specific permission node.
     * @param permissionNode the permission node to check
     * @return boolean whether they have the permission node
     */
    boolean hasPermission(String permissionNode);

    void openInventory(Inventory inventory);
}
