package net.bteuk.api;

import java.util.UUID;

public interface BTEPlayer {

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
     * Get the {@link BTEServer} the player is connected to.
     * @return the server
     */
    BTEServer getServer();

    /**
     * Check if the player has a specific permission node.
     * @param permissionNode the permission node to check
     * @return boolean whether they have the permission node
     */
    boolean hasPermission(String permissionNode);

    void openInventory(BTEInventory<?> BTEInventory);
}
